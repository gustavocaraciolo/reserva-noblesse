<template>
  <div>
    <h2 id="page-heading" data-cy="TorreHeading">
      <span v-text="$t('reservaNoblesseApp.torre.home.title')" id="torre-heading">Torres</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('reservaNoblesseApp.torre.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TorreCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-torre"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('reservaNoblesseApp.torre.home.createLabel')"> Create a new Torre </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && torres && torres.length === 0">
      <span v-text="$t('reservaNoblesseApp.torre.home.notFound')">No torres found</span>
    </div>
    <div class="table-responsive" v-if="torres && torres.length > 0">
      <table class="table table-striped" aria-describedby="torres">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('reservaNoblesseApp.torre.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('reservaNoblesseApp.torre.apartamento')">Apartamento</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="torre in torres" :key="torre.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TorreView', params: { torreId: torre.id } }">{{ torre.id }}</router-link>
            </td>
            <td>{{ torre.numero }}</td>
            <td>
              <div v-if="torre.apartamento">
                <router-link :to="{ name: 'ApartamentoView', params: { apartamentoId: torre.apartamento.id } }">{{
                  torre.apartamento.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TorreView', params: { torreId: torre.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TorreEdit', params: { torreId: torre.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(torre)"
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
        ><span id="reservaNoblesseApp.torre.delete.question" data-cy="torreDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-torre-heading" v-text="$t('reservaNoblesseApp.torre.delete.question', { id: removeId })">
          Are you sure you want to delete this Torre?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-torre"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTorre()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./torre.component.ts"></script>
