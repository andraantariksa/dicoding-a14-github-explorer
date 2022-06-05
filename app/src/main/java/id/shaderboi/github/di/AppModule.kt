package id.shaderboi.github.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.github.data.data_source.db.GithubExplorerDatabase
import id.shaderboi.github.data.data_source.network.GithubService
import id.shaderboi.github.data.repository.FavoriteRepositoryImpl
import id.shaderboi.github.data.repository.GithubRepositoryImpl
import id.shaderboi.github.data.repository.SettingsRepositoryImpl
import id.shaderboi.github.domain.repository.FavoriteRepository
import id.shaderboi.github.domain.repository.GithubRepository
import id.shaderboi.github.domain.repository.SettingsRepository
import id.shaderboi.github.domain.usecase.favorite.*
import id.shaderboi.github.domain.usecase.user.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideGithubService(moshi: Moshi): GithubService =
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GithubService::class.java)

    @Provides
    fun provideGithubRepository(githubService: GithubService): GithubRepository =
        GithubRepositoryImpl(githubService)

    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository =
        SettingsRepositoryImpl(context)

    @Provides
    fun provideGithubExplorerDatabase(@ApplicationContext context: Context): GithubExplorerDatabase =
        Room
            .databaseBuilder(
                context,
                GithubExplorerDatabase::class.java,
                GithubExplorerDatabase.NAME
            )
            .build()

    @Provides
    fun provideFavoriteRepository(database: GithubExplorerDatabase): FavoriteRepository =
        FavoriteRepositoryImpl(database)

    @Provides
    fun provideFavoriteUseCases(favoriteRepository: FavoriteRepository): FavoriteUserUseCases =
        FavoriteUserUseCases(
            getFavoriteUsers = GetFavoriteUsersUseCase(favoriteRepository),
            getFavoriteUser = GetFavoriteUserUseCase(favoriteRepository),
            removeFavoriteUser = RemoveFavoriteUserUseCase(favoriteRepository),
            addFavoriteUser = AddFavoriteUserUseCase(favoriteRepository)
        )

    @Provides
    fun provideUserUseCases(githubRepository: GithubRepository): UserUseCases =
        UserUseCases(
            getUsers = GetUsersUseCase(githubRepository),
            getUser = GetUserUseCase(githubRepository),
            searchUser = SearchUserUseCase(githubRepository),
            getUserFollowers = GetUserFollowersUseCase(githubRepository),
            getUserFollowing = GetUserFollowingUseCase(githubRepository),
        )
}