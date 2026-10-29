package id.co.evolution.mygithubuserapps.feature.users.data.mapper

import id.co.evolution.mygithubuserapps.feature.users.data.local.UserEntity
import id.co.evolution.mygithubuserapps.feature.users.data.remote.dto.UserDetailDto
import id.co.evolution.mygithubuserapps.feature.users.data.remote.dto.UserDto
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUser
import id.co.evolution.mygithubuserapps.feature.users.domain.model.GithubUserDetail

fun UserDto.toDomain(): GithubUser {
    return GithubUser(
        id = id,
        username = login,
        avatarUrl = avatarUrl
    )
}

fun UserDetailDto.toDomain(): GithubUserDetail {
    return GithubUserDetail(
        username = login.orEmpty(),
        name = name ?: "No Name",
        avatarUrl = avatarUrl.orEmpty(),
        bio = bio ?: "No bio available",
        location = location ?: "Unknown",
        company = company ?: "-",
        followers = followers ?: 0,
        following = following ?: 0,
        publicRepos = publicRepos ?: 0
    )
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = login,
        avatarUrl = avatarUrl
    )
}

fun UserDetailDto.toEntity(): UserEntity {
    return UserEntity(
        id = id ?: 0,
        username = login.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        name = name,
        bio = bio,
        company = company,
        location = location,
        followers = followers,
        following = following,
        publicRepos = publicRepos
    )
}

fun UserEntity.toDomain(): GithubUser {
    return GithubUser(
        id = id,
        username = username,
        avatarUrl = avatarUrl
    )
}

fun UserEntity.toDetailDomain(): GithubUserDetail {
    return GithubUserDetail(
        username = username,
        name = name ?: "No Name",
        avatarUrl = avatarUrl,
        bio = bio ?: "No bio available",
        location = location ?: "Unknown",
        company = company ?: "-",
        followers = followers ?: 0,
        following = following ?: 0,
        publicRepos = publicRepos ?: 0
    )
}
