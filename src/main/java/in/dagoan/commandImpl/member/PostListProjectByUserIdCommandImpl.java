package in.dagoan.commandImpl.member;

import in.dagoan.command.member.PostListProjectByUserIdCommand;
import in.dagoan.entity.document.Member;
import in.dagoan.model.request.member.PostListProjectWithUserIdRequest;
import in.dagoan.model.response.member.PostListProjectWithUserIdResponse;
import in.dagoan.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostListProjectByUserIdCommandImpl implements PostListProjectByUserIdCommand {
    private MemberRepository memberRepository;

    @Autowired
    public PostListProjectByUserIdCommandImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Mono<PostListProjectWithUserIdResponse> execute(PostListProjectWithUserIdRequest request) {
        return memberRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(createNewPojectList(request))
                .map(member -> addProjectList(member, request))
                .flatMap(member -> memberRepository.save(member))
                .map(this::toResponse);
    }

    private Mono<Member> createNewPojectList(PostListProjectWithUserIdRequest request) {
        log.info("#PostMemberCommand - Create new member list for user {}", request.getUserId());
        return Mono.fromCallable(() -> toMember(request))
                .flatMap(member -> memberRepository.save(member));
    }

    private Member toMember(PostListProjectWithUserIdRequest request) {
        List<UUID> stringList = new ArrayList<>();
        Member member = Member.builder()
                .memberId(UUID.randomUUID())
                .listProjectId(stringList)
                .userId(request.getUserId())
                .build();

        BeanUtils.copyProperties(request, member);
        return member;
    }

    private Member addProjectList(Member member, PostListProjectWithUserIdRequest request) {
        List<UUID> listProject = member.getListProjectId();
        listProject.add(request.getProjectId());
        member.setListProjectId(listProject);
        return member;
    }

    private PostListProjectWithUserIdResponse toResponse(Member member) {
        PostListProjectWithUserIdResponse response = new PostListProjectWithUserIdResponse();
        response.setMember(member);
        return response;
    }
}
