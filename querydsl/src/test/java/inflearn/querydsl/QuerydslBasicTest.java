package inflearn.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory jpaQueryFactory;
    @Autowired
    private QuerydslApplication querydslApplication;


    @BeforeEach
    public void before() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);


        QMember member = new QMember("m");
   /*   Q클래스 인스턴스를 사용하는 2가지 방법
        QMember qMember = new QMember("m"); //별칭 직접 지정
        QMember qMember = QMember.member; //기본 인스턴스 사용
*/
        Member findmember = jpaQueryFactory
                .select(member)
                .from()
                .where(member.username.eq("member1"))
                .fetchOne();
        assert(findmember.getUsername().equals("member1"));

    }
}