package com.wannago.repository;

import com.wannago.entity.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthRepository extends JpaRepository<Oauth, Integer> {
}
