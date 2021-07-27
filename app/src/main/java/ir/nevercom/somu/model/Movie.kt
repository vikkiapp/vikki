package ir.nevercom.somu.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: MovieCollection?,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("certification")
    val certification: String?,
    @SerializedName("credits")
    val credits: Credits?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) : Parcelable

@Parcelize
data class Credits(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>
) : Parcelable

@Parcelize
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso: String,
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso: String,
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
data class Cast(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable

@Parcelize
data class Crew(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("job")
    val job: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
) : Parcelable

@Parcelize
data class MovieCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?
) : Parcelable


val sampleMovie: Movie = Gson().fromJson(
    """{
  "adult": false,
  "backdrop_path": "https://image.tmdb.org/t/p/w1280/keIxh0wPr2Ymj0Btjh4gW7JJ89e.jpg",
  "belongs_to_collection": null,
  "budget": 200000000,
  "genres": [
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 12,
      "name": "Adventure"
    },
    {
      "id": 53,
      "name": "Thriller"
    },
    {
      "id": 878,
      "name": "Science Fiction"
    }
  ],
  "homepage": "https://www.marvel.com/movies/black-widow",
  "id": 497698,
  "imdb_id": "tt3480822",
  "original_language": "en",
  "original_title": "Black Widow",
  "overview": "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
  "popularity": 5789.087,
  "poster_path": "https://image.tmdb.org/t/p/w500/qAZ0pzat24kLdO3o8ejmbLxyOac.jpg",
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2021-07-07",
  "revenue": 268096199,
  "runtime": 134,
  "spoken_languages": [
    {
      "english_name": "English",
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "english_name": "Russian",
      "iso_639_1": "ru",
      "name": "Pусский"
    }
  ],
  "status": "Released",
  "tagline": "She's Done Running From Her Past.",
  "title": "Black Widow",
  "video": false,
  "vote_average": 8.0,
  "vote_count": 3148,
  "credits": {
    "cast": [
      {
        "adult": false,
        "gender": 1,
        "id": 1245,
        "known_for_department": "Acting",
        "name": "Scarlett Johansson",
        "original_name": "Scarlett Johansson",
        "popularity": 58.866,
        "profile_path": "https://image.tmdb.org/t/p/w185/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg",
        "cast_id": 3,
        "character": "Natasha Romanoff / Black Widow",
        "credit_id": "5a57030fc3a3685ed703622e",
        "order": 0
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1373737,
        "known_for_department": "Acting",
        "name": "Florence Pugh",
        "original_name": "Florence Pugh",
        "popularity": 39.211,
        "profile_path": "https://image.tmdb.org/t/p/w185/75PvULemW8BvheSKtPMoBBsvPLh.jpg",
        "cast_id": 20,
        "character": "Yelena Belova",
        "credit_id": "5ca5267fc3a3685aad0d51ca",
        "order": 1
      },
      {
        "adult": false,
        "gender": 1,
        "id": 3293,
        "known_for_department": "Acting",
        "name": "Rachel Weisz",
        "original_name": "Rachel Weisz",
        "popularity": 30.5,
        "profile_path": "https://image.tmdb.org/t/p/w185/3QbFXeiUzXUVUrJ7fdiCn7A7ReW.jpg",
        "cast_id": 22,
        "character": "Melina Vostokoff",
        "credit_id": "5ca526999251412df01ef61f",
        "order": 2
      },
      {
        "adult": false,
        "gender": 2,
        "id": 35029,
        "known_for_department": "Acting",
        "name": "David Harbour",
        "original_name": "David Harbour",
        "popularity": 9.623,
        "profile_path": "https://image.tmdb.org/t/p/w185/chPekukMF5SNnW6b22NbYPqAStr.jpg",
        "cast_id": 21,
        "character": "Alexei Shostakov / Red Guardian",
        "credit_id": "5ca5268e0e0a26015ca98b26",
        "order": 3
      },
      {
        "adult": false,
        "gender": 2,
        "id": 5538,
        "known_for_department": "Acting",
        "name": "Ray Winstone",
        "original_name": "Ray Winstone",
        "popularity": 6.952,
        "profile_path": "https://image.tmdb.org/t/p/w185/eSRgAC98u5hQroeZzDeRf60XE21.jpg",
        "cast_id": 25,
        "character": "Dreykov",
        "credit_id": "5d0cfeaa9251413945bb0f62",
        "order": 4
      },
      {
        "adult": false,
        "gender": 1,
        "id": 18182,
        "known_for_department": "Acting",
        "name": "Olga Kurylenko",
        "original_name": "Olga Kurylenko",
        "popularity": 22.916,
        "profile_path": "https://image.tmdb.org/t/p/w185/q0QXFRg5bSnaLjbvhamfclt0eId.jpg",
        "cast_id": 329,
        "character": "Antonia Dreykov",
        "credit_id": "6069b5f112c604006ead6a64",
        "order": 5
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1743933,
        "known_for_department": "Acting",
        "name": "Ever Anderson",
        "original_name": "Ever Anderson",
        "popularity": 9.451,
        "profile_path": "https://image.tmdb.org/t/p/w185/qfwlXH1mmJkzmToXsCN7A6IGvcg.jpg",
        "cast_id": 308,
        "character": "Young Natasha",
        "credit_id": "5eb9c53e0cb3350021cf23e6",
        "order": 6
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2131391,
        "known_for_department": "Acting",
        "name": "Violet McGraw",
        "original_name": "Violet McGraw",
        "popularity": 10.339,
        "profile_path": "https://image.tmdb.org/t/p/w185/8Nu8Z6FFEmsO5nZgJZDKHvzYUb0.jpg",
        "cast_id": 291,
        "character": "Young Yelena",
        "credit_id": "5de7fae6a7e363001472852b",
        "order": 7
      },
      {
        "adult": false,
        "gender": 2,
        "id": 113970,
        "known_for_department": "Acting",
        "name": "O.T. Fagbenle",
        "original_name": "O.T. Fagbenle",
        "popularity": 7.126,
        "profile_path": "https://image.tmdb.org/t/p/w185/baIiZBDYpYXfG4SlonbcqyYg6l.jpg",
        "cast_id": 23,
        "character": "Mason",
        "credit_id": "5cae5c059251412fa620ea12",
        "order": 8
      },
      {
        "adult": false,
        "gender": 2,
        "id": 227,
        "known_for_department": "Acting",
        "name": "William Hurt",
        "original_name": "William Hurt",
        "popularity": 9.052,
        "profile_path": "https://image.tmdb.org/t/p/w185/j3mjmuHLBW4XQSw53C8Sh0Lh3ZQ.jpg",
        "cast_id": 163,
        "character": "Secretary Thaddeus 'Thunderbolt' Ross",
        "credit_id": "5d936e859603310023a73d87",
        "order": 9
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2072566,
        "known_for_department": "Acting",
        "name": "Ryan Kiera Armstrong",
        "original_name": "Ryan Kiera Armstrong",
        "popularity": 5.223,
        "profile_path": "https://image.tmdb.org/t/p/w185/eEPDO9H1lKez2i85QA4AUkR8Gv8.jpg",
        "cast_id": 331,
        "character": "Young Antonia",
        "credit_id": "6090920a7390c00078261650",
        "order": 10
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1245,
        "known_for_department": "Acting",
        "name": "Scarlett Johansson",
        "original_name": "Scarlett Johansson",
        "popularity": 58.866,
        "profile_path": "https://image.tmdb.org/t/p/w185/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg",
        "cast_id": 3,
        "character": "Natasha Romanoff / Black Widow",
        "credit_id": "5a57030fc3a3685ed703622e",
        "order": 0
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1373737,
        "known_for_department": "Acting",
        "name": "Florence Pugh",
        "original_name": "Florence Pugh",
        "popularity": 39.211,
        "profile_path": "https://image.tmdb.org/t/p/w185/75PvULemW8BvheSKtPMoBBsvPLh.jpg",
        "cast_id": 20,
        "character": "Yelena Belova",
        "credit_id": "5ca5267fc3a3685aad0d51ca",
        "order": 1
      },
      {
        "adult": false,
        "gender": 1,
        "id": 3293,
        "known_for_department": "Acting",
        "name": "Rachel Weisz",
        "original_name": "Rachel Weisz",
        "popularity": 30.5,
        "profile_path": "https://image.tmdb.org/t/p/w185/3QbFXeiUzXUVUrJ7fdiCn7A7ReW.jpg",
        "cast_id": 22,
        "character": "Melina Vostokoff",
        "credit_id": "5ca526999251412df01ef61f",
        "order": 2
      },
      {
        "adult": false,
        "gender": 2,
        "id": 35029,
        "known_for_department": "Acting",
        "name": "David Harbour",
        "original_name": "David Harbour",
        "popularity": 9.623,
        "profile_path": "https://image.tmdb.org/t/p/w185/chPekukMF5SNnW6b22NbYPqAStr.jpg",
        "cast_id": 21,
        "character": "Alexei Shostakov / Red Guardian",
        "credit_id": "5ca5268e0e0a26015ca98b26",
        "order": 3
      },
      {
        "adult": false,
        "gender": 2,
        "id": 5538,
        "known_for_department": "Acting",
        "name": "Ray Winstone",
        "original_name": "Ray Winstone",
        "popularity": 6.952,
        "profile_path": "https://image.tmdb.org/t/p/w185/eSRgAC98u5hQroeZzDeRf60XE21.jpg",
        "cast_id": 25,
        "character": "Dreykov",
        "credit_id": "5d0cfeaa9251413945bb0f62",
        "order": 4
      },
      {
        "adult": false,
        "gender": 1,
        "id": 18182,
        "known_for_department": "Acting",
        "name": "Olga Kurylenko",
        "original_name": "Olga Kurylenko",
        "popularity": 22.916,
        "profile_path": "https://image.tmdb.org/t/p/w185/q0QXFRg5bSnaLjbvhamfclt0eId.jpg",
        "cast_id": 329,
        "character": "Antonia Dreykov",
        "credit_id": "6069b5f112c604006ead6a64",
        "order": 5
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1743933,
        "known_for_department": "Acting",
        "name": "Ever Anderson",
        "original_name": "Ever Anderson",
        "popularity": 9.451,
        "profile_path": "https://image.tmdb.org/t/p/w185/qfwlXH1mmJkzmToXsCN7A6IGvcg.jpg",
        "cast_id": 308,
        "character": "Young Natasha",
        "credit_id": "5eb9c53e0cb3350021cf23e6",
        "order": 6
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2131391,
        "known_for_department": "Acting",
        "name": "Violet McGraw",
        "original_name": "Violet McGraw",
        "popularity": 10.339,
        "profile_path": "https://image.tmdb.org/t/p/w185/8Nu8Z6FFEmsO5nZgJZDKHvzYUb0.jpg",
        "cast_id": 291,
        "character": "Young Yelena",
        "credit_id": "5de7fae6a7e363001472852b",
        "order": 7
      },
      {
        "adult": false,
        "gender": 2,
        "id": 113970,
        "known_for_department": "Acting",
        "name": "O.T. Fagbenle",
        "original_name": "O.T. Fagbenle",
        "popularity": 7.126,
        "profile_path": "https://image.tmdb.org/t/p/w185/baIiZBDYpYXfG4SlonbcqyYg6l.jpg",
        "cast_id": 23,
        "character": "Mason",
        "credit_id": "5cae5c059251412fa620ea12",
        "order": 8
      },
      {
        "adult": false,
        "gender": 2,
        "id": 227,
        "known_for_department": "Acting",
        "name": "William Hurt",
        "original_name": "William Hurt",
        "popularity": 9.052,
        "profile_path": "https://image.tmdb.org/t/p/w185/j3mjmuHLBW4XQSw53C8Sh0Lh3ZQ.jpg",
        "cast_id": 163,
        "character": "Secretary Thaddeus 'Thunderbolt' Ross",
        "credit_id": "5d936e859603310023a73d87",
        "order": 9
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2072566,
        "known_for_department": "Acting",
        "name": "Ryan Kiera Armstrong",
        "original_name": "Ryan Kiera Armstrong",
        "popularity": 5.223,
        "profile_path": "https://image.tmdb.org/t/p/w185/eEPDO9H1lKez2i85QA4AUkR8Gv8.jpg",
        "cast_id": 331,
        "character": "Young Antonia",
        "credit_id": "6090920a7390c00078261650",
        "order": 10
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2475107,
        "known_for_department": "Acting",
        "name": "Liani Samuel",
        "original_name": "Liani Samuel",
        "popularity": 0.743,
        "profile_path": "https://image.tmdb.org/t/p/w185/x80TD1jUsZwJOn1NnrU6f8h6tY1.jpg",
        "cast_id": 290,
        "character": "Lerato",
        "credit_id": "5de7d4d3a313b8001291c43b",
        "order": 11
      },
      {
        "adult": false,
        "gender": 1,
        "id": 101065,
        "known_for_department": "Acting",
        "name": "Michelle Lee",
        "original_name": "Michelle Lee",
        "popularity": 2.013,
        "profile_path": "https://image.tmdb.org/t/p/w185/r6QzFK8IBxO1Kp3zm3GJ3FUuwwR.jpg",
        "cast_id": 128,
        "character": "Oksana",
        "credit_id": "5d7bd6690443c900107113e4",
        "order": 12
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2752012,
        "known_for_department": "Crew",
        "name": "Lewis Young",
        "original_name": "Lewis Young",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/te6OwBPLWZlQuVKxxff3dcwHM7a.jpg",
        "cast_id": 340,
        "character": "Scientist Morocco 1",
        "credit_id": "60e80c5543250f00765ad409",
        "order": 13
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1074617,
        "known_for_department": "Acting",
        "name": "C.C. Smiff",
        "original_name": "C.C. Smiff",
        "popularity": 2.846,
        "profile_path": "https://image.tmdb.org/t/p/w185/glhejpqIphvfpeOagIvxC1gHJyo.jpg",
        "cast_id": 339,
        "character": "Scientist Morocco 2",
        "credit_id": "60e5cd76a3b5e6005d9145f7",
        "order": 14
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1309110,
        "known_for_department": "Acting",
        "name": "Nanna Blondell",
        "original_name": "Nanna Blondell",
        "popularity": 0.711,
        "profile_path": "https://image.tmdb.org/t/p/w185/llxgGKg9MtCu4v7fT93FPvhfZhI.jpg",
        "cast_id": 136,
        "character": "Ingrid",
        "credit_id": "5d82a39af6787a00269e583a",
        "order": 15
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2408958,
        "known_for_department": "Acting",
        "name": "Simona Zivkovska",
        "original_name": "Simona Zivkovska",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/iPyqxBiuqk3eThlo5j3T528QO0G.jpg",
        "cast_id": 124,
        "character": "Widow",
        "credit_id": "5d796f90069f0e000f340459",
        "order": 16
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416749,
        "known_for_department": "Crew",
        "name": "Erin Jameson",
        "original_name": "Erin Jameson",
        "popularity": 0.98,
        "profile_path": null,
        "cast_id": 343,
        "character": "Widow",
        "credit_id": "60ea723fce5d82004700740f",
        "order": 17
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2410230,
        "known_for_department": "Acting",
        "name": "Shaina West",
        "original_name": "Shaina West",
        "popularity": 2.219,
        "profile_path": "https://image.tmdb.org/t/p/w185/A2XMk7PktS7s8AeGfv30dEFCdlL.jpg",
        "cast_id": 129,
        "character": "Widow",
        "credit_id": "5d7c1da4d5191f2f513dfb3f",
        "order": 18
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2410139,
        "known_for_department": "Acting",
        "name": "Yolanda Lynes",
        "original_name": "Yolanda Lynes",
        "popularity": 8.648,
        "profile_path": "https://image.tmdb.org/t/p/w185/vYMAWq6c1mp4YFQgb4kaTFjXna.jpg",
        "cast_id": 127,
        "character": "Widow",
        "credit_id": "5d7bd06933ec264c2b7eb558",
        "order": 19
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2890978,
        "known_for_department": "Crew",
        "name": "Claudia Heinz",
        "original_name": "Claudia Heinz",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 344,
        "character": "Widow",
        "credit_id": "60ea725985090f0073195e7a",
        "order": 20
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416303,
        "known_for_department": "Acting",
        "name": "Fatou Bah",
        "original_name": "Fatou Bah",
        "popularity": 0.648,
        "profile_path": "https://image.tmdb.org/t/p/w185/tfM5EaGiUgFJNM2yoAFYIvFH4lS.jpg",
        "cast_id": 140,
        "character": "Widow",
        "credit_id": "5d86a3515a469000219b3048",
        "order": 21
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2187686,
        "known_for_department": "Acting",
        "name": "Jade Ma",
        "original_name": "Jade Ma",
        "popularity": 0.626,
        "profile_path": "https://image.tmdb.org/t/p/w185/r3hkiv1qBdMmYwV9XdXdjMPFhk0.jpg",
        "cast_id": 126,
        "character": "Widow",
        "credit_id": "5d7bcff933ec260012805dbe",
        "order": 22
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1102421,
        "known_for_department": "Acting",
        "name": "Jade Xu",
        "original_name": "Jade Xu",
        "popularity": 1.38,
        "profile_path": "https://image.tmdb.org/t/p/w185/lJBBfW1egrlpHvm9cBv008zqMDv.jpg",
        "cast_id": 345,
        "character": "Widow",
        "credit_id": "60ea7291ce5d8200470074cb",
        "order": 23
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2421427,
        "known_for_department": "Crew",
        "name": "Lucy-Jayne Murray",
        "original_name": "Lucy-Jayne Murray",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 346,
        "character": "Widow",
        "credit_id": "60ea72a79824c8005ff0c0b2",
        "order": 24
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2277896,
        "known_for_department": "Crew",
        "name": "Lucy Cork",
        "original_name": "Lucy Cork",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 347,
        "character": "Widow",
        "credit_id": "60ea72b7d75bd6005d1cf8e9",
        "order": 25
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2069535,
        "known_for_department": "Crew",
        "name": "Eniko Fulop",
        "original_name": "Eniko Fulop",
        "popularity": 0.698,
        "profile_path": null,
        "cast_id": 348,
        "character": "Widow",
        "credit_id": "60ea72cb63536a00801ec41e",
        "order": 26
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1824250,
        "known_for_department": "Crew",
        "name": "Lauren Okadigbo",
        "original_name": "Lauren Okadigbo",
        "popularity": 2.323,
        "profile_path": null,
        "cast_id": 349,
        "character": "Widow",
        "credit_id": "60ea731cce5d820047007631",
        "order": 27
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2881848,
        "known_for_department": "Crew",
        "name": "Agel Aurélia",
        "original_name": "Agel Aurélia",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 350,
        "character": "Widow",
        "credit_id": "60ea733de004a6007545018c",
        "order": 28
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416302,
        "known_for_department": "Acting",
        "name": "Zhanè Samuels",
        "original_name": "Zhanè Samuels",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/nfV2Qb9jD19Km3GKLPMs7OagmCK.jpg",
        "cast_id": 139,
        "character": "Widow",
        "credit_id": "5d86a340172d7f0027479f22",
        "order": 29
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416358,
        "known_for_department": "Acting",
        "name": "Shawarah Battles",
        "original_name": "Shawarah Battles",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/xx69SCBNcGjZeFQwU5uRWPZlqli.jpg",
        "cast_id": 282,
        "character": "Widow",
        "credit_id": "5dac6ac03d35570016718996",
        "order": 30
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2414070,
        "known_for_department": "Acting",
        "name": "Tabby Bond",
        "original_name": "Tabby Bond",
        "popularity": 0.652,
        "profile_path": "https://image.tmdb.org/t/p/w185/snxRoygwlQCaAZgQQVJPxslkN6G.jpg",
        "cast_id": 133,
        "character": "Widow",
        "credit_id": "5d8299fa869e7500150c3b07",
        "order": 31
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416393,
        "known_for_department": "Acting",
        "name": "Madeleine Nicholls",
        "original_name": "Madeleine Nicholls",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/vo36T6XfrsGtyHE6JY2Qnh0cNK.jpg",
        "cast_id": 145,
        "character": "Widow",
        "credit_id": "5d86c696336e01002115cb74",
        "order": 32
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2414071,
        "known_for_department": "Acting",
        "name": "Yasmin Riley",
        "original_name": "Yasmin Riley",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/vF2FaxLLkTTTlzH3ozrQzHVpcuQ.jpg",
        "cast_id": 134,
        "character": "Widow",
        "credit_id": "5d829a059e4012001f076a23",
        "order": 33
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2416357,
        "known_for_department": "Acting",
        "name": "Fiona Griffiths",
        "original_name": "Fiona Griffiths",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/eP5IKdONRaq5WAVc7jBxmDZJqpj.jpg",
        "cast_id": 351,
        "character": "Widow",
        "credit_id": "60ea735553f8330045eab725",
        "order": 34
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2414076,
        "known_for_department": "Acting",
        "name": "Georgia Curtis",
        "original_name": "Georgia Curtis",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/qJZFgX97uFxEIz0uKsw597XifzG.jpg",
        "cast_id": 135,
        "character": "Widow",
        "credit_id": "5d829b7bf6787a001e9e74b1",
        "order": 35
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3153321,
        "known_for_department": "Acting",
        "name": "Svetlana Constantine",
        "original_name": "Svetlana Constantine",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 352,
        "character": "Widow",
        "credit_id": "60ea73716c19ea00810ce494",
        "order": 36
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1585047,
        "known_for_department": "Acting",
        "name": "Ione Butler",
        "original_name": "Ione Butler",
        "popularity": 1.4,
        "profile_path": null,
        "cast_id": 313,
        "character": "Widow",
        "credit_id": "5f7e1cc0d11e0e0037bc7d5a",
        "order": 37
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1845814,
        "known_for_department": "Acting",
        "name": "Aubrey Cleland",
        "original_name": "Aubrey Cleland",
        "popularity": 1.648,
        "profile_path": "https://image.tmdb.org/t/p/w185/fC27EDthUrNj9eeP2vNiBr7f8P1.jpg",
        "cast_id": 328,
        "character": "Widow",
        "credit_id": "603ebb2f0ed2ab0031be13db",
        "order": 38
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2068598,
        "known_for_department": "Acting",
        "name": "Kurt Yue",
        "original_name": "Kurt Yue",
        "popularity": 0.93,
        "profile_path": "https://image.tmdb.org/t/p/w185/kPy0d5uUNiJJB18T55IdE01Ruru.jpg",
        "cast_id": 357,
        "character": "Ross Lieutenant",
        "credit_id": "60ebca859824c800465de6bb",
        "order": 39
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2622281,
        "known_for_department": "Acting",
        "name": "Doug Robson",
        "original_name": "Doug Robson",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 358,
        "character": "Ohio Agent",
        "credit_id": "60ebcc9a140bad005ef21b26",
        "order": 40
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1653512,
        "known_for_department": "Acting",
        "name": "Marcel Dorian",
        "original_name": "Marcel Dorian",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 334,
        "character": "Man In BMW",
        "credit_id": "60a4e3999a9e2000295664c9",
        "order": 41
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1361411,
        "known_for_department": "Acting",
        "name": "Zoltán Nagy",
        "original_name": "Zoltán Nagy",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 359,
        "character": "Budapest Clerk",
        "credit_id": "60ebccebcae6320074a9d40b",
        "order": 42
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1972102,
        "known_for_department": "Acting",
        "name": "Liran Nathan",
        "original_name": "Liran Nathan",
        "popularity": 0.764,
        "profile_path": "https://image.tmdb.org/t/p/w185/4pcKpaZkqObqhkOpkkv7qIOfmZh.jpg",
        "cast_id": 33,
        "character": "Mechanic",
        "credit_id": "5d51ed0d77e1f60015ae5cdf",
        "order": 43
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3154658,
        "known_for_department": "Acting",
        "name": "Judit Varga-Szathmary",
        "original_name": "Judit Varga-Szathmary",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 360,
        "character": "Mechanic's Wife",
        "credit_id": "60ebcd1653f8330020ac08ce",
        "order": 44
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3154659,
        "known_for_department": "Acting",
        "name": "Noel Krisztian Kozak",
        "original_name": "Noel Krisztian Kozak",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 361,
        "character": "Mechanic's Child",
        "credit_id": "60ebcd24903c52007d48d22c",
        "order": 45
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2395142,
        "known_for_department": "Acting",
        "name": "Martin Razpopov",
        "original_name": "Martin Razpopov",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/2DW6vLwnBEgfQpiXSgYRDrDU6sY.jpg",
        "cast_id": 52,
        "character": "Tattoo Gulag Inmate",
        "credit_id": "5d618dc454f6eb00149bb07d",
        "order": 46
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2074238,
        "known_for_department": "Acting",
        "name": "Olivier Richters",
        "original_name": "Olivier Richters",
        "popularity": 1.389,
        "profile_path": "https://image.tmdb.org/t/p/w185/8Zz5WYE1Muc3AX9daxCxgNTRE5.jpg",
        "cast_id": 34,
        "character": "Ursa Major",
        "credit_id": "5d51ee9077e1f60013ae6976",
        "order": 47
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3154660,
        "known_for_department": "Acting",
        "name": "Dalibor Bajunovic",
        "original_name": "Dalibor Bajunovic",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 362,
        "character": "Gulag Inmate",
        "credit_id": "60ebcd9053f83300749a1957",
        "order": 48
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1052209,
        "known_for_department": "Acting",
        "name": "Andrew Byron",
        "original_name": "Andrew Byron",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/6p5LO85IeXqir68HvnFWDCc6Dwj.jpg",
        "cast_id": 274,
        "character": "Gulag Cookie Guard",
        "credit_id": "5da08740cdf2e6001d69a688",
        "order": 49
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3154662,
        "known_for_department": "Acting",
        "name": "Ed Ashe",
        "original_name": "Ed Ashe",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 363,
        "character": "Strudel Gulag Guard",
        "credit_id": "60ebcdb0bc2cb3005e7b52b7",
        "order": 50
      },
      {
        "adult": false,
        "gender": 0,
        "id": 132185,
        "known_for_department": "Crew",
        "name": "Dawid Szatarski",
        "original_name": "Dawid Szatarski",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 364,
        "character": "Red Room Scientist 1",
        "credit_id": "60ebcdf00e64af0073bce7af",
        "order": 51
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1797832,
        "known_for_department": "Acting",
        "name": "Cali Nelle",
        "original_name": "Cali Nelle",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 365,
        "character": "Red Room Scientist 2",
        "credit_id": "60ebcdfa9824c800465deeb6",
        "order": 52
      },
      {
        "adult": false,
        "gender": 2,
        "id": 3154663,
        "known_for_department": "Acting",
        "name": "Geoffrey D. Williams",
        "original_name": "Geoffrey D. Williams",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 366,
        "character": "Ross Agent",
        "credit_id": "60ebce3542d8a5004617a8e8",
        "order": 53
      },
      {
        "adult": false,
        "gender": 2,
        "id": 97844,
        "known_for_department": "Acting",
        "name": "Robert Pralgo",
        "original_name": "Robert Pralgo",
        "popularity": 2.823,
        "profile_path": "https://image.tmdb.org/t/p/w185/lF2UcQemojihJM4XlLGkWEEsJuJ.jpg",
        "cast_id": 367,
        "character": "Ross Agent",
        "credit_id": "60ebce45194186007dfe2048",
        "order": 54
      },
      {
        "adult": false,
        "gender": 1,
        "id": 1457277,
        "known_for_department": "Acting",
        "name": "Jacinte Blankenship",
        "original_name": "Jacinte Blankenship",
        "popularity": 0.728,
        "profile_path": "https://image.tmdb.org/t/p/w185/6UZdl5MU1kTptbCN6J1qktDeh4p.jpg",
        "cast_id": 368,
        "character": "Ross Agent",
        "credit_id": "60ebce4c140bad0046f374ca",
        "order": 55
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1666472,
        "known_for_department": "Acting",
        "name": "Josh Henry",
        "original_name": "Josh Henry",
        "popularity": 1.283,
        "profile_path": "https://image.tmdb.org/t/p/w185/sTNvs8688s5jlsORMzfaZf1RCQg.jpg",
        "cast_id": 369,
        "character": "Ross Agent",
        "credit_id": "60ebce545e1200004603e03c",
        "order": 56
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1336371,
        "known_for_department": "Acting",
        "name": "Jose Miguel Vasquez",
        "original_name": "Jose Miguel Vasquez",
        "popularity": 1.052,
        "profile_path": "https://image.tmdb.org/t/p/w185/cv9kMHjsttfgJLy8gPRWdnEwic.jpg",
        "cast_id": 370,
        "character": "Ross Agent",
        "credit_id": "60ebce5d01b1ca002d3a1d35",
        "order": 57
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3153322,
        "known_for_department": "Acting",
        "name": "Valentina Herrera",
        "original_name": "Valentina Herrera",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 353,
        "character": "Young Widow",
        "credit_id": "60ea73c253f8330074971cf8",
        "order": 58
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3008599,
        "known_for_department": "Acting",
        "name": "Danielle Jalade",
        "original_name": "Danielle Jalade",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 354,
        "character": "Young Widow",
        "credit_id": "60ea73dc5e1200007412432e",
        "order": 59
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2443554,
        "known_for_department": "Acting",
        "name": "Aria Brooks",
        "original_name": "Aria Brooks",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 355,
        "character": "Street Kid",
        "credit_id": "60ea7400ce5d82005f5f128a",
        "order": 60
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3153324,
        "known_for_department": "Acting",
        "name": "Sophie Colgrove",
        "original_name": "Sophie Colgrove",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 356,
        "character": "Street Kid",
        "credit_id": "60ea741ba14e10005e9b598e",
        "order": 61
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3154664,
        "known_for_department": "Acting",
        "name": "Caister Myung Choi",
        "original_name": "Caister Myung Choi",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 371,
        "character": "Street Kid",
        "credit_id": "60ebcec8903c5200461de4d3",
        "order": 62
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2425004,
        "known_for_department": "Acting",
        "name": "David Turner",
        "original_name": "David Turner",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 269,
        "character": "SWAT Team (uncredited)",
        "credit_id": "5d97d36d55937b0021337175",
        "order": 63
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2430184,
        "known_for_department": "Acting",
        "name": "Edward L. Oliver",
        "original_name": "Edward L. Oliver",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/s64c2yiGaozakdNegRejZC18stH.jpg",
        "cast_id": 275,
        "character": "SWAT Team Leader (uncredited)",
        "credit_id": "5da0875aae3668001320e82d",
        "order": 64
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2430185,
        "known_for_department": "Acting",
        "name": "Dale Liner",
        "original_name": "Dale Liner",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/33bwgXBeu1Jrot1b6Zcy1xR8Qtq.jpg",
        "cast_id": 276,
        "character": "SWAT Team Leader (uncredited)",
        "credit_id": "5da0876e4a2226001fe0fb72",
        "order": 65
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2443836,
        "known_for_department": "Acting",
        "name": "Rob Horrocks",
        "original_name": "Rob Horrocks",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/rliI97z19GjRmqTD2yAKsm2qMbe.jpg",
        "cast_id": 286,
        "character": "Polish Soldier (uncredited)",
        "credit_id": "5db832e52d37210015e951d6",
        "order": 66
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2515055,
        "known_for_department": "Acting",
        "name": "Oliver Simms",
        "original_name": "Oliver Simms",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 306,
        "character": "Super Soldier (uncredited)",
        "credit_id": "5e24e2e65ed8e9001761004e",
        "order": 67
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2807152,
        "known_for_department": "Acting",
        "name": "Yuuki Luna",
        "original_name": "Yuuki Luna",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/o3vsR47ZLOWSduz5xpeaMfZXPVD.jpg",
        "cast_id": 314,
        "character": "Widow (uncredited)",
        "credit_id": "5f7e1d27021cee0036466a55",
        "order": 68
      },
      {
        "adult": false,
        "gender": 1,
        "id": 2541004,
        "known_for_department": "Acting",
        "name": "Kalina Vanska",
        "original_name": "Kalina Vanska",
        "popularity": 1.4,
        "profile_path": null,
        "cast_id": 326,
        "character": "Commuter (uncredited)",
        "credit_id": "60106642d55c3d0040765eac",
        "order": 69
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2482808,
        "known_for_department": "Acting",
        "name": "Jordyn Curet",
        "original_name": "Jordyn Curet",
        "popularity": 0.75,
        "profile_path": "https://image.tmdb.org/t/p/w185/t9qacVvl5pjx1LHbrGsOXnL2yUr.jpg",
        "cast_id": 332,
        "character": "Young Ingrid (uncredited)",
        "credit_id": "60909235168ea3004240bcc3",
        "order": 70
      },
      {
        "adult": false,
        "gender": 0,
        "id": 3133711,
        "known_for_department": "Acting",
        "name": "Chad J. Wagner",
        "original_name": "Chad J. Wagner",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 335,
        "character": "Neighbor (uncredited)",
        "credit_id": "60d2c52f957e6d0030b4fa8f",
        "order": 71
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1853975,
        "known_for_department": "Acting",
        "name": "Joakim Skarli",
        "original_name": "Joakim Skarli",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/kcDgMDDxYnNJooDwKNoABWRf8BG.jpg",
        "cast_id": 299,
        "character": "Russian Soldier (uncredited)",
        "credit_id": "5e1144d8c740d900130a07c5",
        "order": 72
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2807157,
        "known_for_department": "Acting",
        "name": "Ian Wilson",
        "original_name": "Ian Wilson",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 316,
        "character": "Russian Spetnaz (uncredited)",
        "credit_id": "5f7e1e66b7abb5003767618d",
        "order": 73
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2433119,
        "known_for_department": "Acting",
        "name": "Omar Alboukharey",
        "original_name": "Omar Alboukharey",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/eTnObsfj2ZPrII1NJ7ohK4hp8A1.jpg",
        "cast_id": 279,
        "character": "Flamboyant Prisoner (uncredited)",
        "credit_id": "5da5d78fcb3084001396e1b8",
        "order": 74
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2430186,
        "known_for_department": "Acting",
        "name": "Tyrone Kearns",
        "original_name": "Tyrone Kearns",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 277,
        "character": "Prisoner (uncredited)",
        "credit_id": "5da087814a2226001fe0fbdf",
        "order": 75
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2464186,
        "known_for_department": "Acting",
        "name": "Tony McCarthy",
        "original_name": "Tony McCarthy",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 289,
        "character": "Prisoner (uncredited)",
        "credit_id": "5dd7edb0ef8b3200188b4150",
        "order": 76
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1497349,
        "known_for_department": "Acting",
        "name": "Gavin Lee Lewis",
        "original_name": "Gavin Lee Lewis",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/snwH9NgMV6gS3KXJj0kG7aiicEP.jpg",
        "cast_id": 137,
        "character": "Prisoner (uncredited)",
        "credit_id": "5d83a0e7798c940220e86fd7",
        "order": 77
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2251721,
        "known_for_department": "Acting",
        "name": "Wong Charlie",
        "original_name": "Wong Charlie",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 125,
        "character": "Prisoner (uncredited)",
        "credit_id": "5d799e86af432400109838f8",
        "order": 78
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2433114,
        "known_for_department": "Acting",
        "name": "Ahmed Bakare",
        "original_name": "Ahmed Bakare",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/upy0oFY2Z1Vg67MQXYv6c164LkQ.jpg",
        "cast_id": 278,
        "character": "Prisoner (uncredited)",
        "credit_id": "5da5d76a1967570013b01607",
        "order": 79
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2807164,
        "known_for_department": "Acting",
        "name": "Zoltan Rencsar",
        "original_name": "Zoltan Rencsar",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 320,
        "character": "Prisoner (uncredited)",
        "credit_id": "5f7e1fd7944a575d2eca6cb2",
        "order": 80
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2434215,
        "known_for_department": "Acting",
        "name": "Adam Prickett",
        "original_name": "Adam Prickett",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/pDXhEuwFIEByE91tWLCqK0YyG6H.jpg",
        "cast_id": 280,
        "character": "Russian Prisoner (uncredited)",
        "credit_id": "5da749dde6d3cc0013ad8525",
        "order": 81
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2355281,
        "known_for_department": "Acting",
        "name": "Luigi Boccanfuso",
        "original_name": "Luigi Boccanfuso",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 284,
        "character": "Gulag Prisoner (uncredited)",
        "credit_id": "5db1a51dcb30840013ad737b",
        "order": 82
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1651385,
        "known_for_department": "Acting",
        "name": "Roman Green",
        "original_name": "Roman Green",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/uUd7UsRw94bvbluAkntDDdOJpM0.jpg",
        "cast_id": 301,
        "character": "Gulag Arm Wrestling Inmate (uncredited)",
        "credit_id": "5e18f2632ea6b90013eee741",
        "order": 83
      },
      {
        "adult": false,
        "gender": 0,
        "id": 1394361,
        "known_for_department": "Acting",
        "name": "Clem So",
        "original_name": "Clem So",
        "popularity": 0.884,
        "profile_path": "https://image.tmdb.org/t/p/w185/elyNvdqbWo2v2teDKFQ3jpoapU0.jpg",
        "cast_id": 325,
        "character": "Gulag Inmate (uncredited)",
        "credit_id": "5f8b73a090fca3003707a6bf",
        "order": 84
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2807159,
        "known_for_department": "Acting",
        "name": "Graham Kitchen",
        "original_name": "Graham Kitchen",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 317,
        "character": "Gulag Inmate #4 (uncredited)",
        "credit_id": "5f7e1eacd11e0e0038bc426a",
        "order": 85
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2303017,
        "known_for_department": "Acting",
        "name": "Daniel Joseph Woolf",
        "original_name": "Daniel Joseph Woolf",
        "popularity": 0.982,
        "profile_path": "https://image.tmdb.org/t/p/w185/it2F1ZSOqFp68td0IdfgniooE4d.jpg",
        "cast_id": 287,
        "character": "Gulag Inmate #120 (uncredited)",
        "credit_id": "5db8330ad2c0c10016189f49",
        "order": 86
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2424172,
        "known_for_department": "Acting",
        "name": "John Wolfe",
        "original_name": "John Wolfe",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/pAi854GfMOaNf9FoNQ2PAWyQCCl.jpg",
        "cast_id": 217,
        "character": "Guard (uncredited)",
        "credit_id": "5d960d452c6b7b0013936844",
        "order": 87
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1841458,
        "known_for_department": "Acting",
        "name": "Paul O'Kelly",
        "original_name": "Paul O'Kelly",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 132,
        "character": "Riot Guard (uncredited)",
        "credit_id": "5d7fb7cca3d0270012dca580",
        "order": 88
      },
      {
        "adult": false,
        "gender": 0,
        "id": 2443835,
        "known_for_department": "Acting",
        "name": "Shane Askam",
        "original_name": "Shane Askam",
        "popularity": 0.6,
        "profile_path": null,
        "cast_id": 285,
        "character": "Gulag Riot Guard (uncredited)",
        "credit_id": "5db832c7a1d3320011e7005e",
        "order": 89
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2506333,
        "known_for_department": "Acting",
        "name": "Obie Matthew",
        "original_name": "Obie Matthew",
        "popularity": 1.4,
        "profile_path": "https://image.tmdb.org/t/p/w185/dMf7uPLi74oLJcB67zMyRgcsO2u.jpg",
        "cast_id": 300,
        "character": "Prison Riot Guard (uncredited)",
        "credit_id": "5e18f2333679a1001393bee6",
        "order": 90
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2417290,
        "known_for_department": "Acting",
        "name": "Ty Hurley",
        "original_name": "Ty Hurley",
        "popularity": 0.994,
        "profile_path": "https://image.tmdb.org/t/p/w185/2Hn1wQPJlTd9m1vHs2TrIYC8NK1.jpg",
        "cast_id": 148,
        "character": "Prison Riot Guard (uncredited)",
        "credit_id": "5d88def1b76cbb0027e3ec2c",
        "order": 91
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2063499,
        "known_for_department": "Acting",
        "name": "Stephen Samson",
        "original_name": "Stephen Samson",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/B1Pkaj8hhEVtDhiSFNlxg3tOL9.jpg",
        "cast_id": 307,
        "character": "KGB (uncredited)",
        "credit_id": "5e80a4811d6c5f00181d6089",
        "order": 92
      },
      {
        "adult": false,
        "gender": 2,
        "id": 2545141,
        "known_for_department": "Acting",
        "name": "Marian Lorencik",
        "original_name": "Marian Lorencik",
        "popularity": 0.6,
        "profile_path": "https://image.tmdb.org/t/p/w185/xAQrvjx7EI5vIV8I7ebUyGqTAin.jpg",
        "cast_id": 318,
        "character": "KGB (uncredited)",
        "credit_id": "5f7e1ef6fdfc9f0035b129a7",
        "order": 93
      },
      {
        "adult": false,
        "gender": 1,
        "id": 15886,
        "known_for_department": "Acting",
        "name": "Julia Louis-Dreyfus",
        "original_name": "Julia Louis-Dreyfus",
        "popularity": 3.255,
        "profile_path": "https://image.tmdb.org/t/p/w185/pDzlVQhjxKQBpb9NcQ3hR20kz3z.jpg",
        "cast_id": 330,
        "character": "Contessa Valentina Allegra de Fontaine (uncredited)",
        "credit_id": "6079a8589f37b00057a53156",
        "order": 94
      },
      {
        "adult": false,
        "gender": 2,
        "id": 17604,
        "known_for_department": "Acting",
        "name": "Jeremy Renner",
        "original_name": "Jeremy Renner",
        "popularity": 9.654,
        "profile_path": "https://image.tmdb.org/t/p/w185/ycFVAVMliCCf0zXsKWNLBG3YxzK.jpg",
        "cast_id": 393,
        "character": "Clint Barton / Hawkeye (voice) (uncredited)",
        "credit_id": "60ebdc860e64af004521ab20",
        "order": 95
      }
    ],
    "crew": [
      {
        "adult": false,
        "gender": 1,
        "id": 93286,
        "known_for_department": "Directing",
        "name": "Cate Shortland",
        "original_name": "Cate Shortland",
        "popularity": 2.783,
        "profile_path": "https://image.tmdb.org/t/p/w185/4XvyAyqYH8QDMbo0OwZtRHtbLti.jpg",
        "credit_id": "5b47e3b80e0a267ad500fb11",
        "department": "Directing",
        "job": "Director"
      },
      {
        "adult": false,
        "gender": 1,
        "id": 123132,
        "known_for_department": "Writing",
        "name": "Jac Schaeffer",
        "original_name": "Jac Schaeffer",
        "popularity": 1.303,
        "profile_path": null,
        "credit_id": "5f8025101065d30035ce5804",
        "department": "Writing",
        "job": "Story"
      },
      {
        "adult": false,
        "gender": 2,
        "id": 579281,
        "known_for_department": "Writing",
        "name": "Eric Pearson",
        "original_name": "Eric Pearson",
        "popularity": 2.163,
        "profile_path": "https://image.tmdb.org/t/p/w185/zcDkJjHu28TcrgBi1X4VClUk8Hx.jpg",
        "credit_id": "5e1d43eb4df2910011bf6872",
        "department": "Writing",
        "job": "Screenplay"
      },
      {
        "adult": false,
        "gender": 2,
        "id": 1167896,
        "known_for_department": "Writing",
        "name": "Ned Benson",
        "original_name": "Ned Benson",
        "popularity": 1.951,
        "profile_path": "https://image.tmdb.org/t/p/w185/hXrwsqDs216BGskPQATOj0wEN7y.jpg",
        "credit_id": "5f802518d8e15a0039de0ad3",
        "department": "Writing",
        "job": "Story"
      }
    ]
  },
  "certification": "PG-13"
}""", Movie::class.java
)