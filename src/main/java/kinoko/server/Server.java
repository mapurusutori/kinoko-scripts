package kinoko.server;

import kinoko.database.DatabaseManager;
import kinoko.handler.Dispatch;
import kinoko.provider.EtcProvider;
import kinoko.provider.ItemProvider;
import kinoko.provider.MapProvider;
import kinoko.server.crypto.MapleCrypto;
import kinoko.server.netty.ChannelServer;
import kinoko.server.netty.LoginServer;
import kinoko.world.Account;
import kinoko.world.Channel;
import kinoko.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public final class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static List<World> worlds;
    private static LoginServer loginServer;

    public static List<World> getWorlds() {
        return worlds;
    }

    public static Optional<World> getWorldById(int worldId) {
        return getWorlds().stream()
                .filter(w -> w.getId() == worldId)
                .findFirst();
    }

    public static Optional<Channel> getChannelById(int worldId, int channelId) {
        final Optional<World> worldResult = Server.getWorldById(worldId);
        if (worldResult.isEmpty()) {
            return Optional.empty();
        }
        return worldResult.get().getChannels().stream()
                .filter(ch -> ch.getWorldId() == worldId && ch.getChannelId() == channelId)
                .findFirst();
    }

    public static LoginServer loginServer() {
        return loginServer;
    }

    /**
     * Check whether an {@link Account} instance is associated with a client. In order to prevent multiple clients
     * logging into the same account, this should return true if:
     * <ul>
     *     <li>{@link Account} is authenticated on the {@link LoginServer}, or</li>
     *     <li>{@link MigrationRequest} exists for the account, or</li>
     *     <li>{@link Account} is connected to a {@link ChannelServer} instance.</li>
     * </ul>
     *
     * @param account {@link Account} instance to check.
     * @return true if {@link Account} is currently associated with a client.
     */
    public static boolean isConnected(Account account) {
        if (loginServer.isConnected(account)) {
            return true;
        }
        if (DatabaseManager.migrationAccessor().hasMigrationRequest(account.getId())) {
            return true;
        }
        for (World world : getWorlds()) {
            for (Channel channel : world.getChannels()) {
                if (channel.isConnected(account)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Start migration process, an empty result is returned if migration cannot be performed due to incorrect
     * initialization () or due to existing migrations.
     *
     * @param c           {@link Client} instance attempting to start migration.
     * @param characterId The target character for migration.
     * @return Empty result is returned if migration cannot be performed, result with {@link MigrationRequest} if
     * migration was successfully queued.
     */
    public static Optional<MigrationRequest> submitMigrationRequest(Client c, int characterId) {
        // Account not initialized
        if (c == null || c.getAccount() == null) {
            return Optional.empty();
        }
        // Account not authenticated
        final Account account = c.getAccount();
        if (!account.canSelectCharacter(characterId) || !loginServer.isAuthenticated(c, account)) {
            return Optional.empty();
        }
        // World and Channel not selected
        final Optional<Channel> channelResult = getChannelById(account.getWorldId(), account.getChannelId());
        if (channelResult.isEmpty()) {
            return Optional.empty();
        }
        // Create and Submit MigrationRequest
        final MigrationRequest migrationRequest = new MigrationRequest(
                account.getId(), channelResult.get().getChannelId(), characterId, c.getMachineId(), c.getRemoteAddress()
        );
        if (!DatabaseManager.migrationAccessor().submitMigrationRequest(migrationRequest)) {
            return Optional.empty();
        }
        return Optional.of(migrationRequest);
    }

    /**
     * Check whether a client migration is valid. There should be a {@link MigrationRequest} that matches the requested
     * character ID, the client's machine ID and remote address.
     *
     * @param c           {@link Client} instance attempting to migrate to channel server.
     * @param characterId Target character ID attempting to migrate to channel server.
     * @return {@link MigrationRequest} instance that matches the request.
     */
    public static Optional<MigrationRequest> fetchMigrationRequest(Client c, int characterId) {
        final Optional<MigrationRequest> mrResult = DatabaseManager.migrationAccessor().fetchMigrationRequest(characterId);
        if (mrResult.isEmpty() || !mrResult.get().strictMatch(c, characterId)) {
            return Optional.empty();
        }
        return mrResult;
    }

    public static void main(String[] args) {
        Server.start();
    }

    private static void start() {
        // Load Providers
        Instant start = Instant.now();
        ItemProvider.initialize(false);
        MapProvider.initialize(false);
        EtcProvider.initialize();
        System.gc();
        log.info("Loaded providers in {} milliseconds", Duration.between(start, Instant.now()).toMillis());

        // Load Database
        start = Instant.now();
        DatabaseManager.initialize();
        log.info("Loaded database connection in {} milliseconds", Duration.between(start, Instant.now()).toMillis());

        // Load World
        start = Instant.now();
        MapleCrypto.initialize();
        Dispatch.registerHandlers();
        loginServer = new LoginServer();
        loginServer.start().join();
        log.info("Login server listening on port {}", loginServer.getPort());
        final List<Channel> channels = new ArrayList<>();
        for (int channelId = 0; channelId < ServerConfig.CHANNELS_PER_WORLD; channelId++) {
            final Channel channel = new Channel(
                    (byte) ServerConfig.WORLD_ID,
                    (byte) channelId,
                    ServerConstants.SERVER_ADDRESS,
                    ServerConstants.CHANNEL_PORT + channelId,
                    String.format("%s - %d", ServerConfig.WORLD_NAME, channelId + 1)
            );
            final ChannelServer channelServer = new ChannelServer(channel);
            channelServer.start().join();
            log.info("Channel {} listening on port {}", channelId + 1, channel.getChannelPort());
            channel.setChannelServer(channelServer);
            channels.add(channel);
        }
        worlds = List.of(new World(ServerConfig.WORLD_ID, ServerConfig.WORLD_NAME, Collections.unmodifiableList(channels)));
        log.info("Loaded world in {} milliseconds", Duration.between(start, Instant.now()).toMillis());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Server.stop();
            } catch (ExecutionException | InterruptedException e) {
                log.error("Exception caught while shutting down Server", e);
                throw new RuntimeException(e);
            }
        }));
    }

    private static void stop() throws ExecutionException, InterruptedException {
        log.info("Shutting down Server");
        loginServer.stop().join();
        for (World world : getWorlds()) {
            for (Channel channel : world.getChannels()) {
                channel.getChannelServer().stop().join();
            }
        }
        DatabaseManager.shutdown();
    }
}