package com.comunidadedevspace.taskbeats


import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf(
        Task(0, "Empilhador Toyota", "Atribuição: João"),
        Task(1, "Komatsu Giratoria", "Atribuição: Manuel "),
        Task(2, "Senneboghen Giratoria", "Atribuição : Jose Almeida"),
        Task(3, "Empilhador Eletrico", "Atribuição: Wang"),
        Task(4, "Empilhador Manitou", "Atribuição: Nuno"),
        Task(5, "Senneboghen Giratoria", "Atribuição: Ricardo"),
        Task(6, "Maquina do Baldo", "Atribuição: Sergio"),
        Task(7, "Prensa", "Atribuição: Agny"),
        Task(8, "Balanca pequena", "Atribuição: Jean"),
        Task(9, "Balanca de fora", "Balanca Principal"),

        )

    private lateinit var ctn_content: LinearLayout
    private val adapter: TaskListAdapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // faça algo aqui
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            val newList = arrayListOf<Task>()
                .apply {
                    addAll(taskList)
                }

            newList.remove(task)

            showMessage(ctn_content,"Item Deleted ${task.title}")

            if (newList.size == 0) {
                ctn_content.visibility = View.VISIBLE
            }



            adapter.submitList(newList)

            taskList = newList

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        ctn_content = findViewById(R.id.ctn_content)
        adapter.submitList(taskList)


        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
       rvTask.adapter = adapter


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            openTaskListDetail(null)

        }

    }
    private fun showMessage(view: View,message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }



    private fun onListItemClicked(task: Task) {
       openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)

    }
}


sealed class ActionType : Serializable {
    object DELETE : ActionType()
    object UPTADE : ActionType()
    object CREATE : ActionType()

}

data class TaskAction(val task: Task,
                      val actionType: ActionType) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"