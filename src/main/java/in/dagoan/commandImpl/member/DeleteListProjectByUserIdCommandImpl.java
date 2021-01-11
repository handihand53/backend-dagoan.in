package in.dagoan.commandImpl.member;

import in.dagoan.command.member.DeleteListProjectByUserIdCommad;
import in.dagoan.entity.document.Member;
import in.dagoan.model.request.member.DeleteListProjectWithUserIdRequest;
import in.dagoan.model.response.member.DeleteListProjectWithUserIdResponse;
import in.dagoan.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DeleteListProjectByUserIdCommandImpl implements DeleteListProjectByUserIdCommad {
    private MemberRepository memberRepository;

    @Autowired
    public DeleteListProjectByUserIdCommandImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Mono<DeleteListProjectWithUserIdResponse> execute(DeleteListProjectWithUserIdRequest request) {
        return memberRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Member list not found!")))
                .map(member -> updateMemberList(member, request))
                .flatMap(member -> memberRepository.save(member))
                .map(this::toResponse);
    }

    private Member updateMemberList(Member member, DeleteListProjectWithUserIdRequest request) {
        List<UUID> listMembers = new ArrayList<>();
        member.getListProjectId().forEach(listMember ->{
            if (!checkMemberListToRemoved(listMember, request))
                listMembers.add(listMember);
        });
        member.setListProjectId(listMembers);
        return member;
    }

    private boolean checkMemberListToRemoved(UUID listMember, DeleteListProjectWithUserIdRequest request) {
        return listMember.equals(request.getProjectId());
    }

    private DeleteListProjectWithUserIdResponse toResponse(Member member) {
        DeleteListProjectWithUserIdResponse response = new DeleteListProjectWithUserIdResponse();
        response.setMember(member);
        return response;
    }
}
