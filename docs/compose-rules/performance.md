# Performance Tips

## ✅ Best Practices

### ♻️ 1. Avoid Unnecessary Recompositions

- Don’t recreate data or state in Composables unnecessarily.
- Use `remember`, `rememberUpdatedState`, and `derivedStateOf` appropriately.

### 🧠 2. Use `remember` to Cache Values

- Use `remember` for in-Composable caching.
- Use `rememberSaveable` when the value should survive configuration changes.

### 🔑 3. Use `key()` in Lazy Lists

- Helps Compose reuse items efficiently.
- Prevents full recomposition of list items when the list changes.

### ♻️4. Split large composables
- Split large composables into smaller reusable units.

### 📦 5. Mark Stable Data Classes (Optional)

- Use `@Stable` or ensure your data class has stable equals/hashcode behavior.
- Avoid mutable public properties in models.

---

## ✅ Good Examples

```kotlin

@Immutable
data class User(val name: String, val age: Int)

@Composable
fun ProfileScreen() {
    Header()
    Content()
    Footer()
}


@Composable
fun UserListScreen(filteredItems: List<User>) {
    // Avoid recomputing on every recomposition
    val sortedList = remember(filteredItems) {
        filteredItems.sortedBy { it.name }
    }

    LazyColumn {
        items(sortedList, key = { it.id }) { user ->
            UserCard(user = user)
        }
    }
}
```
## ❌ Bad Examples

```
@Composable
fun InefficientList(filteredItems: List<User>) {
    // ❌ Sorting done on every recomposition!
    val sortedList = filteredItems.sortedBy { it.name }

    LazyColumn {
        items(sortedList) {
            UserCard(user = it)
        }
    }
}

@Composable
fun DynamicImage(url: String) {
    // ❌ This will recreate the painter every time
    val painter = loadImagePainter(url)
    Image(painter = painter, contentDescription = null)
}

LazyColumn {
    // ❌ No key provided — all items will be re-bound on update
    items(userList) {
        UserCard(user = it)
    }
}
```