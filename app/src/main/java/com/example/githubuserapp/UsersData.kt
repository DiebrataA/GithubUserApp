package com.example.githubuserapp

object UsersData {
    private val userNames = arrayOf("Kenny Hemsworth",
            "Liam Neeson",
            "Levi Ackerman",
            "Kirigaya Kazuto",
            "Park Shin Hye",
            "Asuna",
            "Robert Downey Jr.",
            "David",
            "Eren Yaeger",
            "Rimuru Tempest",
            "Dante",
            "Anggara Diebrata",
            "Kimberley Olson",
            "Tom Holland")

    private val userUnames = arrayOf("@kennyH12",
            "@neeson_L",
            "@leviac",
            "@kirito",
            "@ShinPark",
            "@asuna",
            "@ironManisMe",
            "@david34",
            "@bigTitan",
            "@strong_slime",
            "@rebellion",
            "@diebrataA",
            "@kim123",
            "@iamSpiderMan")

    private val company = arrayOf("Swiss Electronics",
            "Warner Technologies",
            "Kyojin Tech",
            "SAO Entertainment",
            "Alhambra Technologies",
            "ALO Tech",
            "Stark Industries",
            "GadgetIn Tech",
            "Sasageyo Entertainment",
            "Slime Industries",
            "DMC Technologies",
            "Four A Technologies",
            "AQUA Industries",
            "Stark Industries")

    private val userLocation = arrayOf("Semarang",
            "DKI Jakarta",
            "Solo",
            "Yogyakarta",
            "DKI Jakarta",
            "Tangerang Selatan",
            "Pekanbaru",
            "Bandung",
            "Depok",
            "Bali",
            "Makassar",
            "Lampung",
            "Bengkulu",
            "Pekanbaru",)

    private val userImages = intArrayOf(R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.avatar5,
            R.drawable.avatar7,
            R.drawable.avatar8,
            R.drawable.avatar9,
            R.drawable.avatar10,
            R.drawable.avatar11,
            R.drawable.avatar12,
            R.drawable.avatar13,
            R.drawable.avatar14,
            R.drawable.avatar15)

    private val followers = arrayOf(90, 89, 80, 76, 123, 102, 100, 99, 1023, 203, 30, 300, 230, 146)

    private val following = arrayOf(9, 8, 12, 10, 12, 20, 101, 90, 16, 30, 3, 1, 200, 14)

    private val repo = arrayOf(12, 32, 42, 23, 11, 10, 6, 22, 43, 40, 13, 15, 21, 14)

//    val listData: ArrayList<Users>
//        get() {
//            val list = arrayListOf<Users>()
//            for (position in userNames.indices){
//                val user = Users()
//                user.usernames = userUnames[position]
//                user.name = userNames[position]
//                user.photo = userImages[position]
//                user.followers = followers[position].toString()
//                user.following = following[position].toString()
//                user.userLocation = userLocation[position]
//                user.company = company[position]
//                user.repo = repo[position].toString()
//                list.add(user)
//            }
//            return list
//        }
}