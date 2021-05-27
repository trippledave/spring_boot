package de.cibek.mscasa.webrtc;

import de.cibek.mscasa.common.security.config.CommonSecurityConfig;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig extends CommonSecurityConfig {

    @Override
    protected List<String> getAllowedPaths() {
        final List<String> paths = super.getAllowedPaths();
        paths.add("/api/v1/test");
        return paths;
    }
}
