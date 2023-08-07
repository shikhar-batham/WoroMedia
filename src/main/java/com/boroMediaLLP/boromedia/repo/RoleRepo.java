package com.boroMediaLLP.boromedia.repo;

import com.boroMediaLLP.boromedia.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {

}
