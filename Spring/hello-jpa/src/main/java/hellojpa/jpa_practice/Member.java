package hellojpa.jpa_practice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "TEST_MEMBER")
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // Team 레퍼런스와 테이블의 FK 와 매핑
    private Team team;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
//    @JoinColumn(name = "locker_id")
    private Locker locker;

    public void addLocker(Locker locker) {
        this.locker = locker;
        locker.setMember(this);
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    protected Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
