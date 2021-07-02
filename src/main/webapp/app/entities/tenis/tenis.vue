<template>
  <div>
    <h2 id="page-heading" data-cy="TenisHeading">
      <span v-text="$t('reservaNoblesseApp.tenis.home.title')" id="tenis-heading">Tenis</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('reservaNoblesseApp.tenis.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TenisCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tenis"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('reservaNoblesseApp.tenis.home.createLabel')"> Create a new Tenis </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tenis && tenis.length === 0">
      <span v-text="$t('reservaNoblesseApp.tenis.home.notFound')">No tenis found</span>
    </div>
    <div class="table-responsive" v-if="tenis && tenis.length > 0">
      <table class="table table-striped" aria-describedby="tenis">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('date')">
              <span v-text="$t('reservaNoblesseApp.tenis.date')">Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('reservaNoblesseApp.tenis.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.login')">
              <span v-text="$t('reservaNoblesseApp.tenis.user')">User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.login'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tenis in tenis" :key="tenis.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TenisView', params: { tenisId: tenis.id } }">{{ tenis.id }}</router-link>
            </td>
            <td>{{ tenis.date }}</td>
            <td>{{ tenis.notes }}</td>
            <td>
              {{ tenis.user ? tenis.user.login : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TenisView', params: { tenisId: tenis.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TenisEdit', params: { tenisId: tenis.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tenis)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="reservaNoblesseApp.tenis.delete.question" data-cy="tenisDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tenis-heading" v-text="$t('reservaNoblesseApp.tenis.delete.question', { id: removeId })">
          Are you sure you want to delete this Tenis?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tenis"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTenis()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="tenis && tenis.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tenis.component.ts"></script>
