package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.PushSetting;
import com.example.Social.Network.API.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushSettingRepo extends JpaRepository<PushSetting,Long> {

    PushSetting findPushSettingByUser(User user);

}
