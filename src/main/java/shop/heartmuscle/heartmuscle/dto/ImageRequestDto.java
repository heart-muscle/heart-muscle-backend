//package shop.heartmuscle.heartmuscle.dto;
//
//import lombok.*;
//import shop.heartmuscle.heartmuscle.domain.Image;
//import shop.heartmuscle.heartmuscle.domain.Workout;
//
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//public class ImageRequestDto {
//    private Long id;
//    private String title;
//    private String filePath;
//    private Workout workout;
//
//    public Image toEntity() {
//        Image build = Image.builder()
//                .id(id)
//                .title(title)
//                .filePath(filePath)
//                .workout(workout)
//                .build();
//        return build;
//    }
//
//    @Builder
//    public ImageRequestDto(Long id, String title, String filePath) {
//        this.id = id;
//        this.title = title;
//        this.filePath = filePath;
//    }
//}
