package com.neobrahma.puzzlegame.di

import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByCellUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByColumnUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByGridUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.FindOnePossibilityByRowUseCase
import com.neobrahma.puzzlegame.domain.sudoku.usecase.resolver.ResolverAlgoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Test {
    @Provides
    fun provideResolverAlgo(): ResolverAlgoUseCase {
        return FindOnePossibilityByCellUseCase(
            FindOnePossibilityByGridUseCase(
                FindOnePossibilityByRowUseCase(
                    FindOnePossibilityByColumnUseCase()
                )
            )
        )
    }
}