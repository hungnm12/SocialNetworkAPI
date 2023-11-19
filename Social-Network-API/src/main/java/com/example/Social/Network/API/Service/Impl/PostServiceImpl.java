package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.*;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetListPostsReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetPostReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.PostResDto.*;
import com.example.Social.Network.API.Repository.ImageRepo;
import com.example.Social.Network.API.Repository.PostRepo;
import com.example.Social.Network.API.Repository.UserRepo;
import com.example.Social.Network.API.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.example.Social.Network.API.utils.JwtUtils.getUserFromToken;


@Service
@Slf4j
public class PostServiceImpl implements PostService {
@Autowired
private JwtService jwtService;

@Autowired
private PostRepo postRepo;

@Autowired
private ImageRepo imageRepo;

@Autowired
private S3Service s3Service;
@Autowired
private UserRepo userRepo;


@Override
    public GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
      try {
          image.getOriginalFilename();
          String urlImg = s3Service.uploadFile(image).get("url");
          Post post1 = new Post();
          Image image1 = new Image();
          image1.setUrlImage(urlImg);

          Video video1 = new Video(urlImg,post1);

          post1.setDescribed(described);
          post1.setStatus(status);
          post1.setUrl(generatePostUrl());
          User user = getUserFromToken(jwtService,userRepo, token);
          post1.setUser(user);
          chargeOnPost(user, post1);
          postRepo.save(post1);


          if (isSufficientSize(image)) {
              return new GeneralResponse(ResponseCode.FILE_SIZE_TOO_BIG,ResponseMessage.FILE_SIZE_TOO_BIG,"");
          }

          if (isSufficientSize(video)) {
              return new GeneralResponse(ResponseCode.FILE_SIZE_TOO_BIG,ResponseMessage.FILE_SIZE_TOO_BIG,"");
          }

          if (user.getCoins() < 1){
              return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS,ResponseMessage.NOT_ENOUGH_COINS,"");
          }

          if (!jwtService.isTokenValid(token , user)){
              return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
          }

          return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, new PostDto(post1.getId(),post1.getUrl(),user.getCoins()));
      }
      catch (RuntimeException e ) {
          return new GeneralResponse(ResponseCode.EXCEPTION_ERROR,"","");

      }
    }


    private boolean isSufficientSize(MultipartFile file) {
        if (file == null) {
            return false;
        }
        long fileSize = file.getSize();
        long fileSizeInMb  = fileSize / (1024 * 1024);

        return fileSizeInMb > 10;
    }

    private String generatePostUrl() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to a string
        String uuidString = uuid.toString();

        // Create a URL using the UUID string
        String url = "https://example.com/posts/" + uuidString;

        return url;
    }


    private void chargeOnPost(User user, Post post) {

        int currentCoin = user.getCoins();

        currentCoin -= 1;

        user.setCoins(currentCoin);

        userRepo.save(user);

    }
// Thieu case block user --> isBlock ,block do violate community standards --> banned, fix return of author
    @Override
    public GeneralResponse getPost(GetPostReqDto getPostReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        Post post = new Post();
        postRepo.getById(getPostReqDto.getId());
        User user = getUserFromToken(jwtService,userRepo, getPostReqDto.getToken() );
        post.setUser(user);



        GetPostResDto getPostResDto = new GetPostResDto();
        getPostResDto.setId(post.getId());
        getPostResDto.setUrl(post.getUrl());
        getPostResDto.setCreated(post.getCreated());
        getPostResDto.setModified(post.getModified());
        getPostResDto.setDisappointed(post.getDissapointed());
        getPostResDto.setKudos(post.getKudos());
        getPostResDto.setFake(Long.valueOf(post.getFake()));
        getPostResDto.setTrust(Long.valueOf(post.getTrust()));
        getPostResDto.setIsMarked(post.isMarked());
        getPostResDto.setIsRated(post.isRated());


        ArrayList<Image> images = post.getImages();
        if (images != null && !images.isEmpty()) {
            List<GetPostResDto.Image> imageResDtos = new ArrayList<>();
            for (Image image : images) {
                GetPostResDto.Image imageResDto = new GetPostResDto.Image();
                imageResDto.setId(image.getId());
                imageResDto.setUrlImage(image.getUrlImage());
                imageResDtos.add(imageResDto);
            }
            getPostResDto.setImage((GetPostResDto.Image) imageResDtos);
        }

        ArrayList<Video> videos = post.getVideos();
        if (videos != null && !videos.isEmpty()) {
            List<GetPostResDto.Video> videoResDtos = new ArrayList<>();
            for (Video video : videos ) {
                GetPostResDto.Video videoResDto = new GetPostResDto.Video();
                videoResDto.setThumb(video.getThumb());
                videoResDto.setUrlVideo(video.getUrlVideo());
                videoResDtos.add(videoResDto);
            }
            getPostResDto.setVideo((GetPostResDto.Video) videoResDtos);
        }

//        User author = post.getUser();
//        List<Post> posts = new ArrayList<>();
//        for (Post userPost : author.getListing()) {
//                userPost.getId(postRepo.getById(posts));
//        }


//        GetPostResDto.Author authorResDto = new GetPostResDto.Author();
//        authorResDto.setId(author.getId());
//
//        authorResDto.setAvatar(author.getAvatar());
//        authorResDto.setCoins(author.getCoins());
//        authorResDto.setListing();
//        getPostResDto.setAuthor(authorResDto);



        if (!jwtService.isTokenValid(getPostReqDto.getToken() , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
    }


        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, getPostResDto);
    }


    // missing banned case, account is locked case, chi? so am

    @Override
    public GeneralResponse editPost(String token, Long Id, String described, String status, MultipartFile image, String image_del, String image_sort, MultipartFile video, String auto_accept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {



        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);

        existPost.setUser(user);

        chargeOnPost(user,existPost);



        if (!image_del.isEmpty()) {
            Image imageToDelete = imageRepo.findById(Long.parseLong(image_del)).get();
            existPost.getImages().remove(imageToDelete);
            imageRepo.delete(imageToDelete);
        }

        if (image != null && !image.isEmpty()) {
            String urlImg = s3Service.uploadFile(image).get("url");
            Image image1 = new Image();
            image1.setUrlImage(urlImg);

            existPost.getImages().add(image1);
        }

        if (!image_sort.isEmpty()) {
            List<Long> imageIds = Arrays.stream(image_sort.split(",")).map(Long::parseLong).collect(Collectors.toList());
            ArrayList<Image> sortedImage = (ArrayList<Image>) existPost
                    .getImages()
                    .stream()
                    .filter(image1 -> imageIds.contains(image1.getId()))
                    .sorted(Comparator.comparingLong(Image::getId))
                    .collect(Collectors.toList());
            existPost.setImages(sortedImage);
        }

//        if (video != null && !video.isEmpty()) {
//            String urlVideo = s3Service.uploadFile(video);
//            Video video1 = new Video(urlVideo, existPost);
//            existPost.setVideos(video1);
//        }

        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

        if (user.getCoins() < 1){
            return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS,ResponseMessage.NOT_ENOUGH_COINS,"");
        }

        if (existPost.getVideos().isEmpty() || existPost.getImages().isEmpty() ||
                (existPost.getVideos().isEmpty()&&existPost.getImages().isEmpty()) ) {
                if (existPost.getDescribed().equals(described) || existPost.getStatus().equals(status)
                        || (existPost.getDescribed().equals(described))&&existPost.getStatus().equals(status)) {
                    existPost.setDescribed(described);
                    existPost.setStatus(status);

                }
                else {
                    return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");
                }

        }

        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

        existPost.setStatus(status);
        existPost.setDescribed(described);

        postRepo.save(existPost);


        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, new EditPostResDto(user.getCoins()));


    }




    // Test case ID, test case 3
    @Override
    public GeneralResponse deletePost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);

        chargeOnPost(user,existPost);

        if (user.getCoins() < 1){
            return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS,ResponseMessage.NOT_ENOUGH_COINS,"");
        }

        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

        postRepo.delete(existPost);

        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new DeletePostResDto(user.getCoins()));
    }

    @Override
    public GeneralResponse reportPost(String token, Long Id, String subject, String details) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);

      existPost.setSubject(subject);
      existPost.setDetails(details);
      existPost.setReported(true);
      postRepo.save(existPost);

        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE);
    }


    @Override
    public GeneralResponse feel(String token, Long Id, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);

        if (type.equals("1")) {
            existPost.setKudos(existPost.getKudos() + 1);
        } else if (type.equals("0")) {
            existPost.setDissapointed(existPost.getDissapointed() + 1);
        } else {
            return new GeneralResponse (ResponseCode.PARAMETER_TYPE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID);
        }

        postRepo.save(existPost);
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new FeelResDto(Math.toIntExact(existPost.getKudos()), Math.toIntExact(existPost.getDissapointed())));
    }



    @Override
    public GeneralResponse getMarkComment(Long Id, String index, String count) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }

    @Override
    public GeneralResponse setMarkComment(String token, Long id, String content, String index, String count, String markId, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        return null;
    }


    // not functional yet
    @Override
    public GeneralResponse getListPosts(GetListPostsReqDto getListPostsReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        Advertisement advertisement = new Advertisement();
        User user = getUserFromToken(jwtService,userRepo, getListPostsReqDto.getToken());

//        getListPostsReqDto.setId(user.getId());
//        getListPostsReqDto.setLatitude(user.getLatitude());
//        getListPostsReqDto.setLongitude(user.getLongitude());
//        getListPostsReqDto.setInCampaign(advertisement.getIn_campaign());




        return new GeneralResponse(null,"");
    }


}
