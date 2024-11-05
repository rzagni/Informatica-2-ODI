# Infa2ODI: Informatica to Oracle Data Integrator (ODI) Migration Toolkit

## Project Overview
Infa2ODI is a toolkit designed to facilitate the migration of Informatica PowerMart projects to Oracle Data Integrator (ODI). This project provides a set of Java classes that programmatically handle the creation, configuration, and mapping of ODI components based on metadata from Informatica projects. This solution streamlines and automates the process, making the migration more efficient and consistent.

## Key Components

### Core Modules
- **`infa2odi.commons`**
  - **`MigrationODI`**: Core utility class that contains helper methods to manage and retrieve ODI elements like projects, folders, models, mappings, and reusable objects. It acts as the foundation for creating and configuring ODI entities during the migration.

### ODI Component Configuration Classes
- **`odi.components`**
  - **`DataStoreSourceODI`**: Responsible for setting up the ODI datastore to represent a source from the Informatica project. Maps Informatica source fields to ODI columns, specifying primary keys, data types, and other properties.
  - **`DataStoreTargetODI`**: Similar to `DataStoreSourceODI`, but designed for target structures. It maps Informatica target fields to an ODI datastore, configuring attributes such as keys and field precision.
  - **`ExpressionODI`**: Manages the creation of ODI expression components, translating Informatica expression transformations into ODI equivalents by mapping field names, expressions, and precision values.
  - **`FilterODI`**: Handles the configuration of ODI filter components. Extracts and sets filter conditions based on Informatica filter transformations.
  - **`MappingODI`**: Manages the creation of ODI mappings, applying relevant properties and descriptions from Informatica mappings to create equivalent ODI structures.
  - **`ModelFolderSourcesODI`** and **`ModelFolderTargetODI`**: These classes are responsible for configuring ODI source and target model folders based on the project’s structure, providing organization within ODI for migrated components.
  - **`ModelODI`**: Facilitates the setup of an ODI model folder for a project, ensuring that models are organized consistently in ODI.
  - **`ProjectFolderODI`**: Manages the setup of folders within ODI projects, organizing files and resources for easier project navigation.
  - **`ProjectODI`**: Creates and configures a new ODI project, based on the properties of the Informatica project.
  - **`ReusableMappingODI`**: Configures reusable mappings within ODI, allowing for complex mappings to be defined once and reused throughout a project.
  - **`VariableODI`**: Manages the creation of variables within an ODI project, mapping over Informatica variables and maintaining their purpose.

### Mapping Utilities
- **`odi.map`**
  - **`ODIComponentType`**: Defines constants representing ODI component types, allowing easy reference to standard components within the toolkit.
  - **`ODIConnectTo`**: Represents connections between source and target components in an ODI mapping, managing source and target table names, column names, and dataset names.
  - **`Project`**: Represents an ODI project structure, capturing key project details like name, code, database type, and folder organization to ensure consistency during the migration process.

### User Interface
- **`infa2odi.ui`**
  - **`infa2odi_ui`** and **`infa2odi_ui_Objects`**: These classes provide a graphical interface for logging into an ODI instance, selecting schemas and files, and initiating the migration process. Users can input connection details and select files to parse for migration to ODI.

## Installation & Usage
1. Clone the repository:
   ```bash
   git clone https://github.com/rzagni/Informatica2ODI.git
   ```
2. Open the project in your preferred Java IDE.
3. Add the necessary ODI libraries and dependencies.
4. Configure connection parameters as needed in `infa2odi_ui`.
5. Run `infa2odi_ui` to access the GUI and begin the migration process.

## License
This project is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. See the full license details in [License.txt](License.txt).

## Author
- **Renzo Zagni** - Primary developer and maintainer of Infa2ODI.

