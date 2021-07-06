/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ReservaUpdateComponent from '@/entities/reserva/reserva-update.vue';
import ReservaClass from '@/entities/reserva/reserva-update.component';
import ReservaService from '@/entities/reserva/reserva.service';

import UserService from '@/admin/user-management/user-management.service';

import EspacoService from '@/entities/espaco/espaco.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Reserva Management Update Component', () => {
    let wrapper: Wrapper<ReservaClass>;
    let comp: ReservaClass;
    let reservaServiceStub: SinonStubbedInstance<ReservaService>;

    beforeEach(() => {
      reservaServiceStub = sinon.createStubInstance<ReservaService>(ReservaService);

      wrapper = shallowMount<ReservaClass>(ReservaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          reservaService: () => reservaServiceStub,

          userService: () => new UserService(),

          espacoService: () => new EspacoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.reserva = entity;
        reservaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reservaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.reserva = entity;
        reservaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reservaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundReserva = { id: 123 };
        reservaServiceStub.find.resolves(foundReserva);
        reservaServiceStub.retrieve.resolves([foundReserva]);

        // WHEN
        comp.beforeRouteEnter({ params: { reservaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.reserva).toBe(foundReserva);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
