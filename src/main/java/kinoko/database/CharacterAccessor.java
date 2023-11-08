package kinoko.database;

import kinoko.world.user.AvatarData;
import kinoko.world.user.CharacterData;

import java.util.List;
import java.util.Optional;

public interface CharacterAccessor {
    Optional<Integer> nextCharacterId();

    Optional<CharacterData> getCharacterById(int characterId);

    Optional<CharacterData> getCharacterByName(String name);

    List<AvatarData> getAvatarDataByAccount(int accountId);

    boolean saveCharacter(CharacterData characterData);
}
