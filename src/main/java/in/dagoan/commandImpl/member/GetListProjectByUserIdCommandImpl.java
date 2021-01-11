package in.dagoan.commandImpl.member;

import in.dagoan.command.member.GetListProjectByUserIdCommand;
import in.dagoan.entity.document.Member;
import in.dagoan.model.request.member.GetListProjectWithUserIdRequest;
import in.dagoan.model.response.member.GetListProjectWithUserIdResponse;
import in.dagoan.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetListProjectByUserIdCommandImpl implements GetListProjectByUserIdCommand {
    private MemberRepository memberRepository;

    @Autowired
    public GetListProjectByUserIdCommandImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Mono<GetListProjectWithUserIdResponse> execute(GetListProjectWithUserIdRequest request) {
        return memberRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Member not found!")))
                .map(member -> toResponse(member, request));
    }

    private GetListProjectWithUserIdResponse toResponse(Member member, GetListProjectWithUserIdRequest request) {
        GetListProjectWithUserIdResponse response = new GetListProjectWithUserIdResponse();
        response.setMember(member);
        return response;
    }
}
