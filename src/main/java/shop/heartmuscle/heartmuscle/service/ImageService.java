package shop.heartmuscle.heartmuscle.service;

//@Service
//@AllArgsConstructor
//public class ImageService {
//
//    private  ImageRepository imageRepository;
//    private CommentRepository commentRepository;
//
//    public void saveImage(ImageRequestDto imageRequestDto) {
//        imageRepository.save(imageRequestDto.toEntity());
//    }
//
//    public List<Image> getImages() {
//        return imageRepository.findAll();
//    }
//
////    @Transactional
////    public void createImageComment(CommentRequestDto commentRequestDto) {
////        Image image = imageRepository.findById(commentRequestDto.getId()).orElseThrow(
////                () -> new NullPointerException("댓글못단다고오오오")
////        );
////
////        Comment comment = new Comment(commentRequestDto, z);
////        commentRepository.save(comment);
////    }
//}