package com.qsl.qsl_tutorial.boundedcontext.user.repository;

import com.qsl.qsl_tutorial.boundedcontext.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
  SiteUser getQslUser(Long id);

  long getQslCount();

  SiteUser getQslUserOrderByIdAscOne();

  List<SiteUser> getQslUserOrderByIdAsc();

  List<SiteUser> searchQsl(String user1);

  Page<SiteUser> searchQsl(String keyword, Pageable pageable);
}
