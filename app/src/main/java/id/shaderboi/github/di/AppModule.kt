package id.shaderboi.github.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.github.data.data_source.network.GithubService
import id.shaderboi.github.data.repository.GithubRepositoryImpl
import id.shaderboi.github.domain.repository.GithubRepository
import id.shaderboi.github.domain.use_case.GetUserUseCase
import id.shaderboi.github.domain.use_case.GetUsersUseCase
import id.shaderboi.github.domain.use_case.UserUseCases
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideGithubService(moshi: Moshi): GithubService =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GithubService::class.java)

    @Provides
    fun provideGithubRepository(githubService: GithubService): GithubRepository =
        GithubRepositoryImpl(githubService)

    @Provides
    fun provideUserUseCases(githubRepository: GithubRepository) =
        UserUseCases(
            getUsers = GetUsersUseCase(githubRepository),
            getUser = GetUserUseCase(githubRepository)
        )
}