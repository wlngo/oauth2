package top.wei.oauth2.configure.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.core.log.LogMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.PersistentLoginsMapper;
import top.wei.oauth2.model.entity.PersistentLogins;

import java.util.Date;

/**
 * @author 魏亮宁
 * @date 2023年12月10日 15:33:00
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {


    private final PersistentLoginsMapper persistentLoginsMapper;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogins persistentLogins = new PersistentLogins();
        persistentLogins.setUsername(token.getUsername())
                .setSeries(token.getSeries())
                .setToken(token.getTokenValue())
                .setLastUsed(token.getDate());
        persistentLoginsMapper.insert(persistentLogins);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {

        persistentLoginsMapper.update(new UpdateWrapper<PersistentLogins>()
                .set("token", tokenValue)
                .set("last_used", lastUsed)
                .eq("series", series));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogins persistentLogins;
        try {
            persistentLogins = persistentLoginsMapper.selectOne(new QueryWrapper<PersistentLogins>().eq("series", seriesId));
            if (persistentLogins == null) {
                log.debug(String.valueOf(LogMessage.format("Querying token for series '%s' returned no results.", seriesId)));
                return null;
            }
            return new PersistentRememberMeToken(persistentLogins.getUsername(), persistentLogins.getSeries(), persistentLogins.getToken(), persistentLogins.getLastUsed());
        } catch (TooManyResultsException tooManyResultsException) {
            log.error(String.valueOf(LogMessage.format(
                    "Querying token for series '%s' returned more than one value. Series" + " should be unique",
                    seriesId)));
        } catch (DataAccessException ex) {
            log.error("Failed to load token for series " + seriesId, ex);
        }
        return null;

    }

    @Override
    public void removeUserTokens(String username) {
        persistentLoginsMapper.delete(new QueryWrapper<PersistentLogins>()
                .eq("username", username));
    }
}
