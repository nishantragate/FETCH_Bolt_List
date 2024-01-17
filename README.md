# _Fetch Bolt List Android App v1.0!_

---

This Android app fetches json data from an AWS S3 URL, processes the data, and displays it in the app UI. You can test the application using the apk provided in the repository **[FetchBoltList_v1.apk](https://github.com/nishantragate/FETCH_Bolt_List/blob/main/FetchBoltList_v1.apk)**

#### Overview:

The app has 2 Activities:

**1. MainActivity:**

- Fetches json data asynchronously
- Displays a list of distinct "listId" values
- Allows selecting a "listId" to see its associated item names

**2. ItemNamesActivity**

- Launched when a "listId" is selected in MainActivity
- Displays the sorted item names associated with the selected "listId" (grouped by listId on the prev screen).
- The DataHandler singleton helps fetch and process json data.

#### Approach:

**MainActivity -** Initializes UI components
- ListView to display listIds
- Creates DataHandler singleton instance
- Fetches json data asynchronously using Executor
- Calls DataHandler's fetchData()
- Updates UI on main thread once data is fetched
- Sets click listener on listIds ListView
- Launches ItemNamesActivity to show item names for selected listId

**ItemNamesActivity -** Gets selected listId from Intent extra
- Calls DataHandler to retrieve item names for given listId
- Displays listId and item names in UI

**DataHandler -** Fetches json data from URL
- Parses into JSONArray and JSONObjects
- Stores item names grouped by listId in HashMap
- Exposes methods to retrieve listIds and item names
- Handles invalid listIds, empty item names etc.
- This modular approach separates data handling from UI, making it reusable. 
- Executing data fetch asynchronously ensures UI remains responsive.

### ✨Screenshots✨

---

#### 1. MainActivity with List ID ListView:

![MainActivity](https://res.cloudinary.com/dprocoztz/image/upload/v1705472040/MainActivity_FetchBoltList_npnuse.jpg)

---

#### 2. ItemNamesActivity with sorted Item Names List View grouped by List ID:

![ItemNamesActivity](https://res.cloudinary.com/dprocoztz/image/upload/v1705472040/ItemNamesActivity_FetchBoltList_hamx4e.jpg)
