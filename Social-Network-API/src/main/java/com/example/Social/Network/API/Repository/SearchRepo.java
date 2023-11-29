package com.example.Social.Network.API.Repository;

import com.example.Social.Network.API.Model.Entity.Search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepo extends JpaRepository<Search, Long> {

    Search findAllById(Long Id);

    Search deleteSearchById(Long Id);







}
