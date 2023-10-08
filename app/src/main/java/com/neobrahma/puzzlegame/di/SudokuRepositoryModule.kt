package com.neobrahma.puzzlegame.di

import com.neobrahma.puzzlegame.domain.sudoku.repository.SudokuRepository
import com.neobrahma.puzzlegame.repository.sudoku.SudokuDataSource
import com.neobrahma.puzzlegame.repository.sudoku.SudokuRepositoryImpl
import com.neobrahma.puzzlegame.repository.sudoku.mock.MockDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SudokuRepositoryModule {
    @Singleton
    @Binds
    fun bindSudokuRepository(impl: SudokuRepositoryImpl)
            : SudokuRepository

    @Singleton
    @Binds
    fun bindSudokuDataSource(impl: MockDataSourceImpl)
            : SudokuDataSource
}