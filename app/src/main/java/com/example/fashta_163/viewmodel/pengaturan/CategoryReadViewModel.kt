package com.example.fashta_163.viewmodel.pengaturan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.Category
import com.example.fashta_163.repository.RepositoryCategory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface StatusUiCategory {
    data class Success(val listCategory: List<Category>) : StatusUiCategory
    object Error : StatusUiCategory
    object Loading : StatusUiCategory
}

class CategoryReadViewModel(
    private val repositoryCategory: RepositoryCategory
) : ViewModel() {

    var statusUiCategory: StatusUiCategory by
    mutableStateOf(StatusUiCategory.Loading)
        private set

    init {
        loadCategory()
    }

    fun loadCategory() {
        viewModelScope.launch {
            statusUiCategory = StatusUiCategory.Loading
            statusUiCategory = try {
                StatusUiCategory.Success(
                    repositoryCategory.getCategory()
                )
            } catch (e: IOException) {
                StatusUiCategory.Error
            } catch (e: HttpException) {
                StatusUiCategory.Error
            }
        }
    }
}