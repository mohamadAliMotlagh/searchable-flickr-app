package com.motlagh.core.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<RQ, RS> {
    suspend operator fun invoke(rq: RQ): Flow<RS>
}

interface ResultUseCase<RQ, RS> {
    suspend operator fun invoke(rq: RQ): Result<RS>
}

interface FlowUseCaseWithoutInput<RS> {
    suspend operator fun invoke(): Flow<RS>
}