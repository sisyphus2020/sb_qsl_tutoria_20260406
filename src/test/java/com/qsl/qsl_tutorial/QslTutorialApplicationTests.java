package com.qsl.qsl_tutorial;

import com.qsl.qsl_tutorial.boundedcontext.user.entity.SiteUser;
import com.qsl.qsl_tutorial.boundedcontext.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class QslTutorialApplicationTests {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("회원 생성")
  void t1() {

    SiteUser u3 = SiteUser.builder()
        .username("user101")
        .password("{noop}1234")
        .email("user101@test.com")
        .build();

    SiteUser u4 = SiteUser.builder()
        .username("user102")
        .password("{noop}1234")
        .email("user102@test.com")
        .build();
    userRepository.saveAll(Arrays.asList(u3, u4));
  }

  @Test
  @DisplayName("1번 회원 Qsl로 가져 오기")
  void t2() {
    // SiteUser u1 = userRepository.findById(1);
    SiteUser u1 = userRepository.getQslUser(1L);

    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");
  }

  @Test
  @DisplayName("2번 회원 Qsl로 가져 오기")
  void t3() {
    // SiteUser u1 = userRepository.findById(1);
    SiteUser u2 = userRepository.getQslUser(2L);

    assertThat(u2.getId()).isEqualTo(2L);
    assertThat(u2.getUsername()).isEqualTo("user2");
    assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    assertThat(u2.getEmail()).isEqualTo("user2@test.com");
  }

  @Test
  @DisplayName("모든 회원 수")
  void t4() {
    long count = userRepository.getQslCount();

    assertThat(count).isGreaterThan(0);
  }

  @Test
  @DisplayName("가장 오래된 회원 1명")
  void t5() {
    SiteUser siteUser = userRepository.getQslUserOrderByIdAscOne();

    assertThat(siteUser.getId()).isEqualTo(1L);
    assertThat(siteUser.getUsername()).isEqualTo("user1");
  }

  @Test
  @DisplayName("전체 회원, 오래된 회원 순")
  void t6() {
    List<SiteUser> users = userRepository.getQslUserOrderByIdAsc();

    SiteUser u1 = users.get(0);

    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");

    SiteUser u2 = users.get(1);
    assertThat(u2.getId()).isEqualTo(2L);
    assertThat(u2.getUsername()).isEqualTo("user2");
    assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    assertThat(u2.getEmail()).isEqualTo("user2@test.com");

  }

  @Test
  @DisplayName("검색, List 리턴, 검색 대상 : username, email")
  void t7() {
    List<SiteUser> users = userRepository.searchQsl("user100");

    assertThat(users.size()).isEqualTo(1L);
    SiteUser u1 = users.get(0);

    assertThat(u1.getUsername()).isEqualTo("user100");
    assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    assertThat(u1.getEmail()).isEqualTo("user100@test.com");

   /* SiteUser u2 = users.get(1);
    assertThat(u2.getId()).isEqualTo(2L);
    assertThat(u2.getUsername()).isEqualTo("user2");
    assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    assertThat(u2.getEmail()).isEqualTo("user2@test.com");*/

  }

  @Test
  @DisplayName("검색, Page 리턴, id asc, pagesize = 10, page =0")
  void t8() {
    long totalCount = userRepository.count();
    int itemInPage = 10;
    int totalPage = (int) Math.ceil((double) totalCount / itemInPage);
    int page = 1;
    String keyWord = "user";

    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.asc("id"));
    Pageable pageable = PageRequest.of(page, itemInPage, Sort.by(sorts));
    Page<SiteUser> usersPage = userRepository.searchQsl(keyWord, pageable);

    assertThat(usersPage.getTotalElements()).isEqualTo(totalCount);
    assertThat(usersPage.getNumber()).isEqualTo(page);
    assertThat(usersPage.getSize()).isEqualTo(itemInPage);
    assertThat(usersPage.getTotalPages()).isEqualTo(totalPage);

    List<SiteUser> users = usersPage.get().toList();
    assertThat(users.size()).isEqualTo(itemInPage);

    SiteUser u1 = users.get(0);
    assertThat(u1.getId()).isEqualTo(11L);
    assertThat(u1.getUsername()).isEqualTo("user11");
    assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    assertThat(u1.getEmail()).isEqualTo("user11@test.com");
  }

  @Test
  @DisplayName("검색, Page 리턴, id asc, pagesize = 10, page =0")
  void t9() {
    long totalCount = userRepository.count();
    int itemInPage = 10;
    int totalPage = (int) Math.ceil((double) totalCount / itemInPage);
    int page = 1;
    String keyWord = "user";

    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("id"));
    Pageable pageable = PageRequest.of(page, itemInPage, Sort.by(sorts));
    Page<SiteUser> usersPage = userRepository.searchQsl(keyWord, pageable);

    assertThat(usersPage.getTotalElements()).isEqualTo(totalCount);
    assertThat(usersPage.getNumber()).isEqualTo(page);
    assertThat(usersPage.getSize()).isEqualTo(itemInPage);
    assertThat(usersPage.getTotalPages()).isEqualTo(totalPage);

    List<SiteUser> users = usersPage.get().toList();
    assertThat(users.size()).isEqualTo(itemInPage);

    SiteUser u1 = users.get(0);
    assertThat(u1.getId()).isEqualTo(90L);
    assertThat(u1.getUsername()).isEqualTo("user90");
    assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    assertThat(u1.getEmail()).isEqualTo("user90@test.com");
  }
}
