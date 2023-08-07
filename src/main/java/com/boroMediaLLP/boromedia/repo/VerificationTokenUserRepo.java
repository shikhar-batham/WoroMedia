package com.boroMediaLLP.boromedia.repo;

import com.boroMediaLLP.boromedia.entity.Users;
import com.boroMediaLLP.boromedia.entity.VerificationTokenUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenUserRepo extends JpaRepository<VerificationTokenUsers, Integer> {

    VerificationTokenUsers findByUsers(Users  user);
}
