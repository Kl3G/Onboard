package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreatePost;
import com.example.Portfolio_Onboard.DTO.DTOModifyPost;
import com.example.Portfolio_Onboard.Entity.EntityFiles;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoFiles;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class ServiceCreatePostImpl implements ServiceCreatePost{

    private final RepoWorld repoWorld;
    private final RepoPost repoPost;
    private final RepoFiles repoFiles;

    public ServiceCreatePostImpl(RepoMemberInfo repoMemberInfo, RepoWorld repoWorld, RepoPost repoPost, RepoFiles repoFiles) {

        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
        this.repoFiles = repoFiles;
    }

    @Override
    public String setPost(DTOCreatePost dtoCreatePost) {

        Optional<EntityWorld> optionalBoard = repoWorld.findById(dtoCreatePost.getBidx());
        EntityWorld board = optionalBoard.get();
        /*Optional<EntityFiles> optionalFiles = repoFiles.findById(dtoCreatePost.getPidx());*/

        Path upPath = Paths.get("/app/data/file"); // 디렉토리 경로만 생성한다

        List<String> ofileList = new ArrayList<>();
        List<String> sfileList = new ArrayList<>();

        for (MultipartFile file : dtoCreatePost.getFiles()) {

            String fileName = file.getOriginalFilename(); // 원본 파일 이름 생성

            if(fileName == null || !fileName.contains(".")){

                continue;
            }

            try {

                Files.createDirectories(upPath); // 생성된 디렉토리 경로로 실제 디렉토리를 만든다.

                ofileList.add(fileName);
                // 원본 파일이름을 O 리스트에 입력

                String ext = fileName.substring(fileName.lastIndexOf("."));
                // 문자열에서 마지막 점(.) 이후의 문자열을 추출하기 위해 사용된다, 이 메서드는 파일의 확장자를 얻는 데 유용하다.
                // 파일형식만 추출
                // System.out.println("ext: "+ ext);

                Long time = System.currentTimeMillis();

                String newFilename = time + ext;
                sfileList.add(newFilename);
                // S 리스트에 입력

                Path targetLocation = upPath.resolve(newFilename); // resolve()는 기존 경로와 새 경로를 붙여서 최종 경로를 만든다.

                Files.copy(file.getInputStream(), targetLocation);
                // 내가 전송한 파일을 복사한 새로운 파일이 서버(내 PC)에 생성된다.
            } catch (Exception e) {

                e.printStackTrace(); // 예외 처리
                // 필요한 경우 예외를 던지거나 로그에 기록합니다.
            }

        }

        // 1. EntityPost 객체 생성
        EntityPost entityPost = dtoCreatePost.entityPost(board, null);
        repoPost.save(entityPost); // 먼저 저장하여 ID가 할당되도록 함
        Long bidx = entityPost.getBoard().getBidx();
        Long pidx = entityPost.getPidx();

        // 2. EntityFiles 객체 생성 후 EntityPost와 연결
        EntityFiles entityFiles = new EntityFiles(dtoCreatePost.getPidx(), entityPost, String.join(",", ofileList), String.join(",", sfileList));
        entityPost.setFiles(entityFiles); // 양방향 관계일 경우 필요
        repoFiles.save(entityFiles); // EntityFiles 저장

        return "redirect:/post?pidx="+pidx+"&bidx="+bidx;
    }

    @Override
    public String updatePost(DTOModifyPost dtoModifyPost) {

        EntityPost post = repoPost.getReferenceById(dtoModifyPost.getPidx());
        post.setPpwd(dtoModifyPost.getPpwd());
        post.setCategory(dtoModifyPost.getCategory());
        post.setText(dtoModifyPost.getText());
        post.setTitle(dtoModifyPost.getTitle());
        Path upPath = Paths.get("/app/data/file");

        // 수정할 때 기존에 있던 파일은 가져와서 삭제
        EntityFiles existingFiles = post.getFiles();
        if (existingFiles != null) {
            String[] existingFileNames = existingFiles.getSfile().split(",");

            // 기존 파일 삭제
            for (String fileName : existingFileNames) {
                try {
                    Files.deleteIfExists(upPath.resolve(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                    // 예외 로그 추가
                }
            }
        }

        List<String> ofileList = new ArrayList<>();
        List<String> sfileList = new ArrayList<>();

        for (MultipartFile file : dtoModifyPost.getFiles()) {

            String fileName = file.getOriginalFilename(); // 원본 파일 이름 생성

            try {

                ofileList.add(fileName);
                // 원본 파일이름을 O 리스트에 입력

                String ext = fileName.substring(fileName.lastIndexOf("."));
                // 문자열에서 마지막 점(.) 이후의 문자열을 추출하기 위해 사용된다, 이 메서드는 파일의 확장자를 얻는 데 유용하다.
                // 파일형식만 추출
                // System.out.println("ext: "+ ext);

                Long time = System.currentTimeMillis();

                String newFilename = time + ext;
                sfileList.add(newFilename);
                // S 리스트에 입력

                Path targetLocation = upPath.resolve(newFilename);

                Files.copy(file.getInputStream(), targetLocation);
                // 내가 전송한 파일을 복사한 새로운 파일이 서버에 생성된다

                /*File destinationFile = new File(fileName);
                file.transferTo(destinationFile); // 실제로 파일을 저장하는 코드*/
            } catch (Exception e) {

                e.printStackTrace(); // 예외 처리
                // 필요한 경우 예외를 던지거나 로그에 기록합니다.
            }

        }

        post.setUserip(dtoModifyPost.getUserip());
        post.setNewdate(new Date());

        Long bidx = post.getBoard().getBidx();
        Long pidx = post.getPidx();
        repoPost.save(post);

        EntityFiles entityFiles = new EntityFiles(dtoModifyPost.getPidx(), post, String.join(",", ofileList), String.join(",", sfileList));
        post.setFiles(entityFiles); // 양방향 관계일 경우 필요
        repoFiles.save(entityFiles); // EntityFiles 저장


        return "redirect:/post?pidx="+pidx+"&bidx="+bidx;
    }

    @Override
    public String delPost(Long pidx, Long bidx) {

        Optional<EntityPost> optionalPost = repoPost.findById(pidx);

        if (optionalPost.isPresent()) {

            EntityPost post = optionalPost.get();

            // 해당 게시글에 연결된 파일 정보 가져오기
            EntityFiles files = post.getFiles();
            if (files != null && !files.getSfile().isEmpty()) {

                // 예: 파일 경로나 파일 이름을 합쳐서 실제 경로를 만든다고 가정
                String filePath = "/app/data/file/" + files.getSfile();
                // 혹은 files.getFilePath()를 사용 (DB에 전체 경로 저장했다면)

                // 물리 경로에 있는 파일 삭제
                File file = new File(filePath);
                if (file.exists()) {

                    boolean deleted = file.delete();
                    log.info("파일 삭제 여부: " + deleted);
                }
            } else {

                log.info("삭제할 파일 없음");
            }

            // 게시글 썸네일 삭제
            File dirThumb = new File("/app/data/image/thumbnail/");
            File[] matchingThumbFiles = dirThumb.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(String.valueOf(post.getPidx()));
                }
            });
            if (Arrays.stream(matchingThumbFiles).findFirst().isPresent()) {

                if(Arrays.stream(matchingThumbFiles).findFirst().get().delete()){

                    log.info("썸네일 삭제 성공");
                }else {

                    log.info("썸네일 삭제 실패");
                }
            }else {

                log.info("삭제할 썸네일 없음");
            }

            // 게시글 이미지 삭제
            Pattern pattern = Pattern.compile("/uploads/" +
                    "([a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}\\.(png|jpg|jpeg|gif|bmp))");
            // 찾고 있는 문자열의 형식(조건)을 지정.

            Matcher matcher = pattern.matcher(post.getText());
            // post.getText()에서 정규표현식에 부합하는 부분을 가져 올 준비.

            List<String> fileNames = new ArrayList<>();
            // matcher 에서 찾은 문자열을 담을 리스트 생성.

            while (matcher.find() /* 찾기 실행 */) {

                fileNames.add(matcher.group(1)); // 찾아서 하나씩 fileNames 에 할당.
            }

            File dirimage = new File("/app/data/image/"); // File 객체는 실제로 파일이나 폴더를 만드는 게 아님.
            // 이 경로에 이런 파일(또는 폴더)이 있다고 가정하고 만든 객체, 경로 정보만 담고 있다.
            // 실제 파일이 존재하는지는 exists(), isFile(), isDirectory() 같은 메서드로 확인.

            File[] matchingImgFiles = dirimage.listFiles((dir, name) -> fileNames.contains(name));
            // dirimage.listFiles((dir, name) -> name.endsWith(".png")); 확장자 조건 걸기
            // dirimage.listFiles((dir, name) -> name.contains("sample")); 파일명에 특정 문자 포함 여부
            // File[] files = dirimage.listFiles(); 필터 없이 전부

            if (matchingImgFiles != null && matchingImgFiles.length != 0) {
                for (File file : matchingImgFiles) {

                    if (file.delete()) {

                        log.info("이미지 삭제 성공: " + file.getName());
                    } else {

                        log.info("이미지 삭제 실패: " + file.getName());
                    }
                }
            }else {

                log.info("삭제할 이미지 없음");
            }


            repoPost.deleteById(pidx);
        }

        return "redirect:/board?bidx="+bidx;
    }

    @Override
    public Optional<EntityPost> createOrUpdate(Long pidx) {

        if (pidx != null && pidx > 0) {

            return repoPost.findById(pidx);
        }
        return Optional.empty();
        /* Optional<PeopleEntity> 타입의 null 처리는 Optional.empty(); 로 해 준다.
        Optional<PeopleEntity> people;처럼 선언만 하면 기본값이 null이 된다.
        이 경우 null 체크를 피할 수 없으며, 나중에 사용 시 NullPointerException이 발생할 위험이 있다.
        따라서, 안전한 코드 작성을 위해 Optional을 선언할 때는 반드시 초기화하는 것이 좋다. */
    }
}
