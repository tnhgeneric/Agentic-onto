# Agentic-onto

## Project Setup Commands and Explanations

1. **Cloned the repository**
   - Command: `git clone https://github.com/tnhgeneric/Agentic-onto.git`
   - Explanation: Cloned the Agentic-onto repository to the local workspace.

2. **Attempted to initialize NX monorepo with preset apps**
   - Command: `npx create-nx-workspace@latest . --preset=apps --nxCloud=false --skip-git`
   - Explanation: Tried to create a new NX workspace in the current directory for managing multiple apps and libraries. The command failed because the directory was not empty and the `--nxCloud` flag was incorrect.

3. **Corrected NX initialization command**
   - Command: `npx create-nx-workspace@latest . --preset=apps --nxCloud=skip --skip-git`
   - Explanation: Attempted to skip NX Cloud setup, but the command still failed because NX requires an empty directory.

4. **Initialized NX in an existing project**
   - Command: `npx nx@latest init`
   - Explanation: Added NX configuration to the existing non-empty project, enabling monorepo management without deleting any files.

5. **Checked the remote repository URL**
   - Command: `git remote -v`
   - Explanation: Verified the current git remote URL to ensure the project is connected to the correct GitHub repository (`https://github.com/tnhgeneric/Agentic-onto.git`).

6. **Ready to push changes to the repository**
   - Next steps: Stage, commit, and push the latest changes (including NX setup and documentation updates) to the remote repository.

7. **Initial commit and push to GitHub**
   - Command: 
     1. `git add .`
     2. `git commit -m "Initial NX monorepo setup with documentation"`
     3. `git push origin main`
   - Explanation: Stages all changes, creates the first commit with a descriptive message, and pushes the code to the main branch on GitHub. This is essential for version control and collaboration, especially for beginners following this guide.

8. **Installed and configured Prettier for code formatting**
   - Command:
     1. `npm install --save-dev prettier`
     2. Created `.prettierrc` with formatting rules.
     3. Created `.prettierignore` to exclude files/folders from formatting.
   - Explanation: Prettier is now set up for automatic code formatting across the monorepo, ensuring consistent code style. The `.prettierrc` file defines formatting preferences, and `.prettierignore` specifies files and folders to skip.

---

## Architecture Overview

### Microfrontend Architecture (Frontend)
- The frontend will use a microfrontend architecture, managed by NX, allowing independent development and deployment of multiple mobile/web apps.
- Expo Go (using the Expo framework) will be used for cross-platform mobile development, supporting modular and scalable app features. Expo Go enables rapid development and testing on both iOS and Android devices without native code compilation.
- Each microfrontend can be developed as a separate app or library within the NX workspace, enabling code sharing and isolated deployments.

### Backend Microservices
- The backend will consist of Spring Boot microservices, each responsible for a specific domain or business capability.
- NX will orchestrate backend services as part of the monorepo, allowing unified scripts, dependency management, and CI/CD.

### Neo4j Aura Integration
- Neo4j Aura will serve as the graph database for Agentic AI, supporting ontology-driven data modeling and relationships.
- Backend microservices will connect to Neo4j Aura using official drivers, enabling advanced graph queries and AI reasoning.
- The ontology layer will be designed to support healthcare-specific knowledge graphs and agentic reasoning.

### Firestore (OLTP)
- Firestore will be used for operational (OLTP) data storage, supporting real-time updates and flexible document models.
- Integration points will be provided in backend services for seamless data flow between Firestore and Neo4j.

---

## NX Monorepo Structure (Best Practice)

Your project now follows the recommended NX structure:

```
apps/
  agentic-frontend/      # Expo Go app (mobile frontend)
  agentic-backend/       # Spring Boot microservice (backend)
libs/
  ...                    # Shared libraries (optional)
```
- All deployable apps (frontend and backend) are under `apps/`.
- Shared code goes in `libs/`.
- This structure is used by leading companies and recommended by the NX team.

---

## Notes
- The workspace is now set up as an NX monorepo, ready to manage both frontend and backend projects.
- Next steps will include scaffolding Expo (React Native) for the frontend and Spring Boot microservices for the backend, with integration for Neo4j Aura and Firestore.