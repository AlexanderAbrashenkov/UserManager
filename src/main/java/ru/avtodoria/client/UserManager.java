package ru.avtodoria.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ru.avtodoria.client.resource.ApplicationResources;
import ru.avtodoria.client.service.UserService;
import ru.avtodoria.client.ui.component.ImageButton;
import ru.avtodoria.shared.FieldVerifier;
import ru.avtodoria.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager implements EntryPoint {
  final HorizontalPanel buttonsPanel = new HorizontalPanel();
  final VerticalPanel mainPanel = new VerticalPanel();
  final FlexTable usersFlexTable = new FlexTable();

  final DialogBox dialogBox = new DialogBox();
  final TextBox lastNameTextBox = new TextBox();
  final TextBox firstNameTextBox = new TextBox();
  final TextBox patronymicTextBox = new TextBox();
  final TextBox birthDateTextBox = new TextBox();
  final Button okButton = new Button("Сохранить");
  HandlerRegistration okButtonHandler;

  final DialogBox messageDialogBox = new DialogBox();
  final Label messageLabel = new Label();

  List<UserDto> userList = new ArrayList<>();

  private final static DateTimeFormat formatter = DateTimeFormat.getFormat("dd.MM.yyyy");

  UserService userService = GWT.create(UserService.class);

  public void onModuleLoad() {
    Defaults.setDateFormat("yyyy-MM-dd");

    ImageButton addButton = new ImageButton(ApplicationResources.INSTANCE.addIcon(), "Добавить");
    addButton.addClickHandler(clickEvent -> addNewUser());

    ImageButton editButton = new ImageButton(ApplicationResources.INSTANCE.editIcon(), "Изменить");
    editButton.addClickHandler(clickEvent -> editUser());

    ImageButton removeButton = new ImageButton(ApplicationResources.INSTANCE.deleteIcon(), "Удалить");
    removeButton.addClickHandler(clickEvent -> removeUsers());

    ImageButton reloadButton = new ImageButton(ApplicationResources.INSTANCE.loadIcon(), "Обновить все");
    reloadButton.addClickHandler(clickEvent -> reloadUsers());

    buttonsPanel.add(addButton);
    buttonsPanel.add(editButton);
    buttonsPanel.add(removeButton);
    buttonsPanel.add(reloadButton);
    buttonsPanel.setSpacing(15);

    usersFlexTable.setText(0, 0, "V");
    usersFlexTable.setText(0,1,"Фамилия");
    usersFlexTable.setText(0,2,"Имя");
    usersFlexTable.setText(0,3,"Отчество");
    usersFlexTable.setText(0,4,"Дата рождения");
    usersFlexTable.getColumnFormatter().setWidth(0, "40px");
    usersFlexTable.getColumnFormatter().setWidth(1, "150px");
    usersFlexTable.getColumnFormatter().setWidth(2, "150px");
    usersFlexTable.getColumnFormatter().setWidth(3, "150px");
    usersFlexTable.getColumnFormatter().setWidth(4, "150px");

    usersFlexTable.getRowFormatter().addStyleName(0, "usersTableHeader");
    usersFlexTable.addStyleName("usersTable");

    mainPanel.add(buttonsPanel);
    mainPanel.add(usersFlexTable);
    mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    mainPanel.getElement().setAttribute("align", "center");

    RootPanel.get("gwtContainer").add(mainPanel);

    createDialogBox();

    createMessageDialogBox();
  }

  private void createDialogBox() {
    dialogBox.setAnimationEnabled(true);
    dialogBox.setGlassEnabled(true);

    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");

    dialogVPanel.add(new Label("Фамилия"));
    lastNameTextBox.getElement().setAttribute("placeholder", "Иванов");
    dialogVPanel.add(lastNameTextBox);
    dialogVPanel.add(new HTML("<br/>"));

    dialogVPanel.add(new Label("Имя"));
    firstNameTextBox.getElement().setAttribute("placeholder", "Иван");
    dialogVPanel.add(firstNameTextBox);
    dialogVPanel.add(new HTML("<br/>"));

    dialogVPanel.add(new Label("Отчество"));
    patronymicTextBox.getElement().setAttribute("placeholder", "Иванович");
    dialogVPanel.add(patronymicTextBox);
    dialogVPanel.add(new HTML("<br/>"));

    dialogVPanel.add(new Label("Дата рождения"));
    birthDateTextBox.getElement().setAttribute("placeholder", "01.01.2000");
    dialogVPanel.add(birthDateTextBox);
    dialogVPanel.add(new HTML("<br/><br/>"));

    HorizontalPanel dialogButtonPanel = new HorizontalPanel();
    dialogButtonPanel.setSpacing(30);
    final Button cancelButton = new Button("Отмена");
    cancelButton.addStyleDependentName("cancel");
    okButton.addStyleDependentName("ok");
    dialogButtonPanel.add(cancelButton);
    dialogButtonPanel.add(okButton);

    dialogVPanel.add(dialogButtonPanel);
    dialogBox.add(dialogVPanel);

    cancelButton.addClickHandler(clickEvent -> {
      clearAndCloseDialogBox();
    });
  }

  private void clearAndCloseDialogBox() {
    lastNameTextBox.setText("");
    firstNameTextBox.setText("");
    patronymicTextBox.setText("");
    birthDateTextBox.setText("");
    dialogBox.hide();
  }

  private void createMessageDialogBox() {
    messageDialogBox.setAnimationEnabled(true);
    messageDialogBox.setGlassEnabled(true);

    messageDialogBox.setText("Что-то пошло не так!");

    VerticalPanel messageVPanel = new VerticalPanel();
    messageVPanel.add(new HTML("<br/>"));
    messageVPanel.add(messageLabel);
    messageVPanel.add(new HTML("<br/>"));
    Button okButton = new Button("OK");
    messageVPanel.add(okButton);

    messageDialogBox.add(messageVPanel);

    okButton.addClickHandler(clickEvent -> {
      messageDialogBox.hide();
    });
  }

  private void showMessage(String message) {
    messageLabel.setText(message);
    messageDialogBox.center();
  }

  private void reloadUsers() {

    userService.getUserList(new MethodCallback<List<UserDto>>() {
      @Override
      public void onFailure(Method method, Throwable throwable) {
        Window.alert("error = " + throwable.getMessage());
      }

      @Override
      public void onSuccess(Method method, List<UserDto> userDtos) {
        Window.alert("response = " + userDtos);
        userList.clear();
        userList.addAll(userDtos);

        refreshUsersTable();
      }
    });
  }

  private void refreshUsersTable() {
    for (int i = usersFlexTable.getRowCount() - 1; i > 0; i--) {
      usersFlexTable.removeRow(i);
    }

    for (UserDto user : userList) {
      addNewUserToTable(user);
    }
  }

  private void removeUsers() {
    List<Integer> rowsToRemove = getCheckedRowIndexes();

    if (rowsToRemove.size() == 0) {
      showMessage("Вы не выбрали пользователей для удаления");
      return;
    } else if (rowsToRemove.size() == 1) {
      String userToRemove = usersFlexTable.getText(rowsToRemove.get(0), 1) + " "
              + usersFlexTable.getText(rowsToRemove.get(0), 2) + " "
              + usersFlexTable.getText(rowsToRemove.get(0), 3);

      boolean canRemove = Window.confirm("Вы действительно хотите удалить пользователя " + userToRemove + "?");
      if (canRemove) {
        removeUsers(rowsToRemove);
      } else {
        return;
      }
    } else {
      boolean canRemove = Window.confirm("Вы действительно хотите удалить " + rowsToRemove.size() + " пользователей?");
      if (canRemove) {
        removeUsers(rowsToRemove);
      } else {
        return;
      }
    }
  }

  private List<Integer> getCheckedRowIndexes() {
    List<Integer> checkedRowsList = new ArrayList<>();
    for (int i = 1; i < usersFlexTable.getRowCount(); i++) {
      CheckBox userCheckBox = (CheckBox) usersFlexTable.getWidget(i, 0);
      if (userCheckBox.getValue())
        checkedRowsList.add(i);
    }
    return checkedRowsList;
  }

  private void removeUsers(List<Integer> rowsToRemove) {
    // TODO: remove from DB

    for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
      usersFlexTable.removeRow(rowsToRemove.get(i));
    }

    for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
      userList.remove(rowsToRemove.get(i) - 1);
    }
  }

  private void editUser() {
    List<Integer> checkedRows = getCheckedRowIndexes();
    if (checkedRows.size() == 0) {
      showMessage("Не выбран пользователь для редактирования");
      return;
    }

    if (checkedRows.size() > 1) {
      showMessage("Можно редактировать только одного пользователя");
      return;
    }

    int row = checkedRows.get(0);
    UserDto user = userList.get(row - 1);

    dialogBox.setText("Редактирование пользователя");
    okButton.setText("Сохранить");
    lastNameTextBox.setText(user.getLastName());
    firstNameTextBox.setText(user.getFirstName());
    patronymicTextBox.setText(user.getPatronymic());
    birthDateTextBox.setText(formatter.format(user.getBirthDate()));
    if (null != okButtonHandler)
      okButtonHandler.removeHandler();

    okButtonHandler = okButton.addClickHandler(clickEvent -> {

      UserDto newUser = createUserWithFieldsCheck();
      if (null == newUser)
        return;

      user.copyUser(newUser);

      boolean userUpdated = false;

      //TODO: edit user in DB
      userService.updateUser(user, new MethodCallback<UserDto>() {
        @Override
        public void onFailure(Method method, Throwable throwable) {
          Window.alert("Не удалось обновить юзера. Ошибка: " + throwable.getMessage());
        }

        @Override
        public void onSuccess(Method method, UserDto userDto) {
          usersFlexTable.setText(row, 1, user.getLastName());
          usersFlexTable.setText(row, 2, user.getFirstName());
          usersFlexTable.setText(row, 3, user.getPatronymic());
          usersFlexTable.setText(row, 4, formatter.format(user.getBirthDate()));

          clearAndCloseDialogBox();
        }
      });
    });
    dialogBox.center();
  }

  private void addNewUser() {
    dialogBox.setText("Добавление пользователя");
    okButton.setText("Добавить");
    if (null != okButtonHandler)
      okButtonHandler.removeHandler();

    okButtonHandler = okButton.addClickHandler(clickEvent -> {

      UserDto newUser = createUserWithFieldsCheck();
      if (null == newUser)
        return;

      //TODO: save new user in DB
      userService.saveUser(newUser, new MethodCallback<Integer>() {
        @Override
        public void onFailure(Method method, Throwable throwable) {
          Window.alert("Не удалось сохранить пользователя. Ошибка: " + throwable.getMessage());
        }

        @Override
        public void onSuccess(Method method, Integer id) {
          newUser.setId(id);
          userList.add(newUser);
          addNewUserToTable(newUser);

          clearAndCloseDialogBox();
        }
      });
    });
    dialogBox.center();
  }

  private void addNewUserToTable(UserDto user) {
    int row = usersFlexTable.getRowCount();
    usersFlexTable.setWidget(row, 0, new CheckBox());
    usersFlexTable.setText(row, 1, user.getLastName());
    usersFlexTable.setText(row, 2, user.getFirstName());
    usersFlexTable.setText(row, 3, user.getPatronymic());
    usersFlexTable.setText(row, 4, formatter.format(user.getBirthDate()));
  }

  private UserDto createUserWithFieldsCheck () {
    String lastName = lastNameTextBox.getText();
    if (!FieldVerifier.isValidName(lastName)) {
      showMessage("Некорректная фамилия");
      return null;
    }

    String firstName = firstNameTextBox.getText();
    if (!FieldVerifier.isValidName(firstName)) {
      showMessage("Некорректное имя");
      return null;
    }

    String patronymic = patronymicTextBox.getText();
    if (!FieldVerifier.isValidName(patronymic)) {
      showMessage("Некорректное отчество");
      return null;
    }

    Date birthDate = FieldVerifier.getDate(birthDateTextBox.getText());
    if (null == birthDate) {
      showMessage("Некорректная дата рождения");
      return null;
    }

    UserDto newUser = new UserDto(lastName, firstName, patronymic, birthDate);

    if (userList.contains(newUser)) {
      showMessage("Такой пользователь уже добавлен");
      return null;
    }

    return newUser;
  }
}
