package id.co.evolution.mygithubuserapps.feature.users.domain.model

data class GithubUserDetail(
    val username: String,
    val name:String,
    val avatarUrl: String,
    val bio: String,
    val location: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val publicRepos: Int
)