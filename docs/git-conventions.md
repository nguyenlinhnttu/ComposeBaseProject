# 🧾 Git Conventions

This document defines the standards for Git  **branch naming** and **commit messages** in this project.

---

## 🌿 Branch Naming Rules

All branches must follow this format:

```
<type>/<issue-id>-<short-description>
```

### ✅ Examples

```
feature/BL123-login-form
fix/BL98-crash-on-load
build/BL201-update-deps
hotfix/BL999-fix-prod-bug
```

### 📌 Allowed `<type>` values

| Type       | Purpose                                          |
| ---------- | ------------------------------------------------ |
| `feature`  | New feature                                      |
| `fix`      | Bug fix                                          |
| `clean`    | Cleanup code, no behavior change                 |
| `refactor` | Code improvements without behavior change        |
| `hotfix`   | Urgent production fixes                          |
| `test`     | Adding or improving tests                        |
| `docs`     | Documentation-only changes                       |
| build      | Update version name, code, update dependence etc |

### 📐 Rules

- Use lowercase and hyphens (`-`) for separation.
- Include issue/task ID if available.
- Keep the description short and meaningful.

---

## 🧪 Example Workflow

1. Pick an issue (or create one).

2. Create a new branch from `develop` or `main`:
   
   ```bash
   git checkout -b feature/BL456-add-dark-mode
   ```

3. Commit changes following the commit message convention.

4. Push and open a pull request.

---

> ✅ These conventions help keep the repository clean, consistent, and automation-friendly. Stick to the rules!

## ✍️ Commit Message Rules

All commit messages must follow this structure:

```
<type>: <optional issue-id> <short summary>

[optional body]

```

### ✅ Example

```
feat: BL123 add JWT token refresh logic

This adds automatic token refresh before expiry, reducing user friction.


```

### 🎯 Allowed `<type>` values

| Type       | Description                               |
| ---------- | ----------------------------------------- |
| `feat`     | A new feature                             |
| `fix`      | A bug fix                                 |
| `docs`     | Documentation changes                     |
| `style`    | Code style changes (formatting, etc.)     |
| `refactor` | Code refactoring (no feature/bug fix)     |
| `perf`     | Performance improvements                  |
| `test`     | Adding or fixing tests                    |
| `clean`    | Tooling, build process, remove  etc.      |
| `ci`       | CI-related changes (GitHub Actions, etc.) |

---

## 
