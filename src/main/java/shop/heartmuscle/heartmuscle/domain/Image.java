//package shop.heartmuscle.heartmuscle.domain;
//
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@Table(name = "Image")
//public class Image extends Timestamped {
//
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 50, nullable = false)
//    private String title;
//
//    @Column(columnDefinition = "TEXT")
//    private String filePath;
//
//    @Column(name = "public", nullable = true)
//    private Public open;
//
//    @Column(name = "workout", nullable = true)
//    private Workout workout;
//
//    @OneToMany(mappedBy = "image")
//    private List<Comment> comments;
//
//    @Builder
//    public Image(Long id, String title, String filePath, Workout workout) {
//        this.id = id;
//        this.title = title;
//        this.filePath = filePath;
//        this.workout = workout;
//    }
//}