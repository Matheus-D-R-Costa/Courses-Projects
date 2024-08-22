package domain;

import java.util.*;

public class Developer {

    private String name;
    private Set<Content> followedContents = new LinkedHashSet<>();
    private Set<Content> completedContents = new LinkedHashSet<>();

    public void follow(Bootcamp bootcamp) {
        this.followedContents.addAll(bootcamp.getContents());
        bootcamp.getFollowedDevelopers().add(this);
    }

    public void progress() {
        Optional<Content> content = this.followedContents.stream().findFirst();
        if (content.isPresent()) {
            this.completedContents.add(content.get());
            this.followedContents.remove(content.get());
        } else {
            System.err.println("Você não está mátriculado em nenhum conteúdo!");
        }
    }

    public double calculateAllXp() {
        Iterator<Content> iterator = this.completedContents.iterator();
        double sum = 0;

        while (iterator.hasNext()) {
            sum += iterator.next().calculateXp();
        }

        return sum;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Content> getFollowedContents() {
        return followedContents;
    }

    public void setFollowedContents(Set<Content> followedContents) {
        this.followedContents = followedContents;
    }

    public Set<Content> getCompletedContents() {
        return completedContents;
    }

    public void setCompletedContents(Set<Content> completedContents) {
        this.completedContents = completedContents;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Developer developer = (Developer) object;
        return Objects.equals(name, developer.name) && Objects.equals(followedContents, developer.followedContents) && Objects.equals(completedContents, developer.completedContents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, followedContents, completedContents);
    }
}
