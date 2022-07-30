package com.example.oauthlogin.service;

import com.example.oauthlogin.model.OAuth2UserGithub;
import com.example.oauthlogin.model.Role;
import com.example.oauthlogin.model.User;
import com.example.oauthlogin.model.UserType;
import com.example.oauthlogin.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserPopulationService {
    @Autowired
    public UserPopulationService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private final RoleRepository roleRepository;


    public void populateUser(User user, OAuth2UserGithub OUser) throws IOException {
        user.setUserType(UserType.GITHUB);
        user.setId(Integer.parseInt(OUser.getName()));
        String login = OUser.getLoginAttribute();
        String avatar_url = OUser.getAvatarUriAttribute();
        user.setUsername(login);
        String profilePicUrl = PictureSaverService.savePicture(avatar_url, login);
        user.setProfilePicUrl(profilePicUrl);
        List<Role> list = new ArrayList<>();
        for (GrantedAuthority authority : OUser.getAuthorities()) {
            String grantedAuthorityAuthority = authority.getAuthority();
            createRoleIfNotFound(grantedAuthorityAuthority, list);


        }
        user.setRoles(list);
    }

    @Transactional
    public void createRoleIfNotFound(String name, List<Role> list) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            Role roleToAdd = new Role(name);
            list.add(roleToAdd);
        }
    }
}
