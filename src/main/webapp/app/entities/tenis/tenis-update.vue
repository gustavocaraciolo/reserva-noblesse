<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="reservaNoblesseApp.tenis.home.createOrEditLabel"
          data-cy="TenisCreateUpdateHeading"
          v-text="$t('reservaNoblesseApp.tenis.home.createOrEditLabel')"
        >
          Create or edit a Tenis
        </h2>
        <div>
          <div class="form-group" v-if="tenis.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tenis.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.tenis.date')" for="tenis-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="tenis-date"
                  v-model="$v.tenis.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="tenis-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.tenis.date.$invalid, invalid: $v.tenis.date.$invalid }"
                v-model="$v.tenis.date.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.tenis.date.$anyDirty && $v.tenis.date.$invalid">
              <small class="form-text text-danger" v-if="!$v.tenis.date.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.tenis.notes')" for="tenis-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="tenis-notes"
              data-cy="notes"
              :class="{ valid: !$v.tenis.notes.$invalid, invalid: $v.tenis.notes.$invalid }"
              v-model="$v.tenis.notes.$model"
            />
            <div v-if="$v.tenis.notes.$anyDirty && $v.tenis.notes.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.tenis.notes.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 140 })"
              >
                This field cannot be longer than 140 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.tenis.user')" for="tenis-user">User</label>
            <select class="form-control" id="tenis-user" data-cy="user" name="user" v-model="tenis.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="tenis.user && userOption.id === tenis.user.id ? tenis.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.tenis.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tenis-update.component.ts"></script>
