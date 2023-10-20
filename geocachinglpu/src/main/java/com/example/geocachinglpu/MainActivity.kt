package com.example.geocachinglpu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geocachinglpu.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var database: DatabaseReference
//    private  var coursename:String?=null
    private lateinit var keylist:ArrayList<qrInfo>
    private lateinit var keysAdapter: keysAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        keylist= arrayListOf<qrInfo>()
        binding.recyclerview.layoutManager= LinearLayoutManager(this)

    binding.getkeys.setOnClickListener(){
        val courseName=binding.coursename.text.toString().trim()
        getKeysData(courseName)
    }


    }

    private fun getKeysData(courseName:String){


        database= FirebaseDatabase.getInstance().getReference("$courseName")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                keylist.clear()
                if(snapshot.exists()){
                    for(keySnap in snapshot.children){
                        val keyData=keySnap
                            .getValue(qrInfo::class.java)
                        keylist.add(keyData!!)
                    }

                    val mAdapter=keysAdapter(keylist)
                    binding.recyclerview.adapter=mAdapter

//                    mAdapter.setOnItemClickListener(object:recyclerAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//                            val intent= Intent(this@CheckDetails,studentDetails::class.java)
//
//                            // put Extra
//                            intent.putExtra("StuReg",StuList[position].registration)
//                            intent.putExtra("StuName",StuList[position].name)
//                            intent.putExtra("StuCourse",StuList[position].course)
//
//                            startActivity(intent)
//                        }
//                    })
//                    binding.rvStu.visibility= View.VISIBLE
//                    binding.tvload.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}