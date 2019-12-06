package teabuddy.utils

import io.realm.DynamicRealm
import io.realm.RealmMigration
import io.realm.RealmObjectSchema

class RealmMigrations: RealmMigration{
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if(oldVersion == 1L){
            /**
             * Migration example:
             * adding age field to realm
             *
             * to read more: https://medium.com/@budioktaviyans/android-realm-migration-schema-4fcef6c61e82
             */
//            val userSchema: RealmObjectSchema? = schema.get("UserData")
//            userSchema?.addField("age", Int::class.java)
        }
    }
}