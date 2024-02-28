package info.deckermail.vehicle_administration.backend.users

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: CrudRepository<RoleEntity, Int>