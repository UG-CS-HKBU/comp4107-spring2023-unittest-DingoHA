import junit.framework.TestCase.assertTrue
import org.junit.Test

class UnitTest {

    @Test
    fun testCaoDodgeAttack() {
        monarchHero = CaoCao()
        heroes.add(monarchHero!!)
        for (i in 0..6)
            heroes.add(NoneMonarchFactory.createRandomHero())

        assertTrue((monarchHero as CaoCao).dodgeAttack())
    }

    @Test
    fun testBeingAttacked() {
        for (i in 0..6)
            heroes.add(NoneMonarchFactory.createRandomHero())

        for(hero in heroes){
            val spy = object: WarriorHero(MinisterRole()) {
                override val name = hero.name
                override fun beingAttacked() {
                    println(hero.beingAttacked())
                    assertTrue(hero.hp >= 0)
                }
            }
            for(i in 0..10){
                spy.beingAttacked()
            }

        }
    }
    @Test
    fun testDiscardCards(){
        val dummyRole = DummyRole()
        val zhangFei = ZhangFei(dummyRole)
        zhangFei.discardCards()
    }
}

object FakeFactory: GameObjectFactory {
    var count = 0
    var last: WeiHero? = null
    init {
        monarchHero = CaoCao()
    }
    override fun getRandomRole(): Role =
        MinisterRole()
    override fun createRandomHero(): Hero {
        val hero = when(count++) {
            0->SimaYi(getRandomRole())
            1->XuChu(getRandomRole())
            else ->XiaHouyuan(getRandomRole())
        }
        if (last == null)
            (monarchHero as CaoCao).helper = hero
        else
            last!!.setNext(hero)
        last = hero
        return hero
    }
}
class CaoCaoUnitTest{
    @Test
    fun testCaoDodgeAttack() {
        var fake = FakeFactory
        monarchHero.setCommand(Abandon(monarchHero))
        heroes.add(monarchHero)
        for (i in 0..2) {
            var hero = fake.createRandomHero()
            //hero.index = heroes.size;
            heroes.add(hero)
        }
        for (hero in heroes) {
            if(hero is CaoCao){
                assert(hero.dodgeAttack())
            }
            hero.templateMethod()
        }
    }
}
class DummyRole : Role {

    override val roleTitle = "Dummy"
    override fun getEnemy(): String {
        return "Dummy, then Dummy"
    }
    // Dummy implementations of other methods
}