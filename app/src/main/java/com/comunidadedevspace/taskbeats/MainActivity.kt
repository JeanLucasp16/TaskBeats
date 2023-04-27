package com.comunidadedevspace.taskbeats


import android.app.Activity
import android.app.Instrumentation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    val taskList = arrayListOf(
        Task(0,"Title0", "Desc0"),
        Task(1,"Title1", "Desc1"),
        Task(2,"Title2", "Desc2"),
        Task(3,"Title3", "Desc3"),
        Task(4,"Title4", "Desc4"),
        Task(5,"Title5", "Desc5"),
        Task(6,"Title6", "Desc6"),
        Task(7,"Title7", "Desc7"),
        Task(8,"Title8", "Desc8"),
        Task(9,"Title9", "Desc9"),
        Task(10,"Title10", "Desc10")
    )
    private val adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // faça algo aqui
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task
            taskList.remove(task)

            adapter.submit(taskList)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        adapter.submit(taskList)


        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
        rvTask.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
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