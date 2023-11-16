package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
}
