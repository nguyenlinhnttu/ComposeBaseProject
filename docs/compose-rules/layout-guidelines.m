# 🎨 UI Layout Guidelines

        Proper use of layout components ensures scalable, readable, and performant UI in Jetpack Compose.

---

## ✅ Best Practices

### 📏 1. Use `Modifier` Wisely

- Chain `Modifier` functions intentionally.
- Start with layout (`fillMaxSize()`, `padding()`), then styling (`background()`, `clip()`), then interaction (`clickable()`).

### ⚖️ 2. Balance Content with `weight()`

- Use `weight()` inside `Row` or `Column` to distribute space evenly or proportionally.

### 🪄 3. Prefer `LazyColumn` / `LazyRow` for Lists

- Avoid using `Column` or `Row` with `scrollable()` for dynamic data.
- `LazyColumn` is memory-efficient and optimized for long lists.

### 🧩 4. Keep Layouts Flat

- Avoid deep Composable nesting like `Column > Row > Box > Column > ...`
- Break into smaller Composables when necessary.

### 🧹 5. Reuse Layouts via Composables

- Extract reusable layouts into parameterized Composable functions.

---

## ✅ Good Examples

```kotlin
// Modifier: ordered chaining
Box(
        modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray)
                .clip(RoundedCornerShape(12.dp))
)

// Balanced Row using weight
Row(modifier = Modifier.fillMaxWidth()) {
Text(
"Left",
modifier = Modifier
        .weight(1f)
        .padding(8.dp)
)
Text(
"Right",
modifier = Modifier
        .weight(1f)
        .padding(8.dp)
)
}

// LazyColumn for user list
LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
) {
items(userList) { user ->
        Text(text = user.name, modifier = Modifier.padding(vertical = 8.dp))
}
}
```
---

## ❌ Bad Examples

```kotlin
// Deep nesting and manual scroll — avoid
Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
    for (user in userList) {
        Row {
            Box {
                Column {
                    Text(user.name)
                }
            }
        }
    }
}

// Using Column for large data set — inefficient
Column {
userList.forEach {
Text(it.name) // ❌ UI will lag with large lists
}
}

// Modifier misuse — wrong order
Box(
        modifier = Modifier
                .background(Color.Blue)
                .padding(16.dp) // ❌ Padding is applied inside the background
                .fillMaxWidth()
)

```