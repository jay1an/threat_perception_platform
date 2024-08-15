package com.tpp.threat_perception_platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpp.threat_perception_platform.dto.DashboardDataDTO;
import com.tpp.threat_perception_platform.mapper.RoleMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Account;
import com.tpp.threat_perception_platform.pojo.LoginUser;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.DashboardDataService;
import com.tpp.threat_perception_platform.service.RoleService;
import com.tpp.threat_perception_platform.service.UserService;
import com.tpp.threat_perception_platform.service.VulnerabilityService;
import com.tpp.threat_perception_platform.utils.JwtUtil;
import com.tpp.threat_perception_platform.utils.RedisCache;
import org.apache.coyote.Response;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@SpringBootTest(classes = ThreatPerceptionPlatformApplication.class)
class ThreatPerceptionPlatformApplicationTests {

}
