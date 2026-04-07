package com.qsl.qsl_tutorial.boundedcontext.user.repository;

import com.qsl.qsl_tutorial.boundedcontext.user.entity.SiteUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.qsl.qsl_tutorial.boundedcontext.user.entity.QSiteUser.siteUser;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public SiteUser getQslUser(Long id) {

    //QSiteUser qSiteUser = QSiteUser.siteUser;

    return jpaQueryFactory
        .selectFrom(siteUser) // SELECT * FROM site_user
        .where(siteUser.id.eq(id)) // WHERE id = 1
        .fetchOne();
  }

  @Override
  public long getQslCount() {

    Long count =  jpaQueryFactory
        .select(siteUser.count())
        .from(siteUser)
        .fetchOne();

    return Optional.ofNullable(count).orElse(0L);
  }

  @Override
  public SiteUser getQslUserOrderByIdAscOne() {

    return jpaQueryFactory
        .select(siteUser) // SELECT * FROM site_user
        .from(siteUser) // FROM site_user
        .orderBy(siteUser.id.asc())
        .limit(1)
        .fetchOne();
  }

  @Override
  public List<SiteUser> getQslUserOrderByIdAsc() {
    return jpaQueryFactory
        .select(siteUser) // SELECT * FROM site_user
        .from(siteUser) // FROM site_user
        .orderBy(siteUser.id.asc())
        .fetch();
  }

  @Override
  public List<SiteUser> searchQsl(String keyword) {
    return jpaQueryFactory
        .select(siteUser) // SELECT * FROM site_user
        .from(siteUser) // FROM site_user
        .where(
            siteUser.username.contains(keyword)
                .or(siteUser.email.contains(keyword)))
        .fetch();
  }

  @Override
  public Page<SiteUser> searchQsl(String keyword, Pageable pageable) {

    // 검색 조건
    BooleanExpression predicate = siteUser.username.containsIgnoreCase(keyword)
        .or(siteUser.email.containsIgnoreCase(keyword));


    // QueryDSL로 데이터 조회
    QueryResults<SiteUser> queryResults = jpaQueryFactory
        .select(siteUser) // SELECT * FROM site_user
        .from(siteUser) // FROM site_user
        .where(predicate)
        .orderBy(siteUser.id.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

    // 결과와 TotalCount를 기반으로 Page 생성
    List<SiteUser> users = queryResults.getResults();

    long total = queryResults.getTotal();

    return new PageImpl<>(users, pageable, total);
  }
}
