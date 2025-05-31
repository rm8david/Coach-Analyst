package com.example.tfg.service;

import com.example.tfg.model.Team;
import com.example.tfg.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServImpl implements TeamService{
    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    @Override
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(int id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> findAllOrderedByPosition() {
        return teamRepository.findAllByOrderByPositionAsc();
    }

    @Override
    public List<Team> findTeamsByTournamentType(String tournamentType) {
        return teamRepository.findByTournamentsTypeOrderByPositionAsc(tournamentType);

    }

    @Override
    public List<Team> findTeamsByTeamTournaments(int teamId) {
        return teamRepository.findTeamsByTeamTournaments(teamId);
    }
}
