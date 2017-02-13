package ch.wisv.areafiftylan.web.committee.controller;

import ch.wisv.areafiftylan.utils.ResponseEntityBuilder;
import ch.wisv.areafiftylan.web.committee.model.CommitteeMember;
import ch.wisv.areafiftylan.web.committee.service.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/web/committee")
public class CommitteeRestController {

    private CommitteeService committeeService;

    @Autowired
    public CommitteeRestController(CommitteeService committeeService) {
        this.committeeService = committeeService;
    }

    /**
     * Create a new CommitteeMember. Only available as admin.
     *
     * @param committeeMember The CommitteeMember to add.
     * @return The status of the addition.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCommitteeMember(@RequestBody CommitteeMember committeeMember) {
        committeeService.addCommitteeMember(committeeMember);
        return ResponseEntityBuilder.createResponseEntity(HttpStatus.CREATED, "Committee member added successfully.", committeeMember);
    }

    /**
     * Retrieve all CommitteeMembers.
     *
     * @return All CommitteeMembers.
     */
    @GetMapping
    public Collection<CommitteeMember> readCommittee() {
        return committeeService.getCommittee();
    }

    /**
     * Update the CommitteeMember at the given id with the given values.
     * Only available as admin.
     *
     * @param id The id at which to update the CommitteeMember.
     * @param committeeMember The new Values for the CommitteeMember.
     * @return The status of the update.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommitteeMember(@PathVariable Long id, @RequestBody CommitteeMember committeeMember) {
        committeeMember = committeeService.updateCommitteeMember(id, committeeMember);
        return ResponseEntityBuilder.createResponseEntity(HttpStatus.ACCEPTED, "Committee member updated successfully.", committeeMember);
    }

    /**
     * Delete a CommitteeMember. Only available as admin.
     *
     * @param id the id of the CommitteeMember to be deleted.
     * @return The status of the deletion.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommitteeMember(@PathVariable Long id) {
        committeeService.deleteCommitteeMember(id);
        return ResponseEntityBuilder.createResponseEntity(HttpStatus.OK, "Committee member deleted successfully.");
    }
}