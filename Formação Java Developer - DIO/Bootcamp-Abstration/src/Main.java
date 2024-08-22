import domain.Bootcamp;
import domain.Course;
import domain.Developer;
import domain.Mentoring;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Course course_01 = new Course();
        course_01.setTitle("Haskell Developer");
        course_01.setDescription("Description Haskell Developer");
        course_01.setWorkload(180);

        Mentoring mentoring_01 = new Mentoring();
        mentoring_01.setTitle("Learn patterns of Haskell");
        mentoring_01.setDescription("Learning patterns of Haskell");
        mentoring_01.setDate(LocalDate.now());

        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setName("Bootcamp Master of Haskell ");
        bootcamp.setDescription("Description Bootcamp Master of Haskell");
        bootcamp.getContents().add(course_01);
        bootcamp.getContents().add(mentoring_01);

        Developer devKylian = new Developer();
        devKylian.setName("Kylian");
        devKylian.follow(bootcamp);
        System.out.println("Conteúdos Inscritos " + devKylian.getName() + devKylian.getFollowedContents());
        devKylian.progress();
        System.out.println("Conteúdos Concluídos " + devKylian.getName() + devKylian.getCompletedContents());
        System.out.println("XP:" + devKylian.calculateAllXp());

        System.out.println("---------");

        Developer devRonnie = new Developer();
        devRonnie.setName("Ronnie");
        devRonnie.follow(bootcamp);
        System.out.println("Conteúdos Inscritos " + devRonnie.getName() + devRonnie.getFollowedContents());
        devRonnie.progress();
        devRonnie.progress();
        System.out.println("Conteúdos Inscritos " + devRonnie.getName() + devRonnie.getCompletedContents());
        System.out.println("XP:" + devRonnie.calculateAllXp());
    }
}
